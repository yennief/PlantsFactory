package org.justynafraczek.plantsshop.payment;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.justynafraczek.plantsshop.payment.models.CancelOrderRequest;
import org.justynafraczek.plantsshop.payment.models.NewPaymentForOrder;
import org.justynafraczek.plantsshop.payment.models.PaymentStatusUpdate;
import org.justynafraczek.plantsshop.payment.models.WarehouseOrderUpdate;

/**
 * A Camel Java DSL Router
 */
public class PaymentRouteBuilder extends RouteBuilder {

    PaymentManager paymentManager = PaymentManager.getInstance();

    private static final String KAFKA_BROKER = System.getenv("KAFKA_BROKER_URL");

    private String topic(String topic) {
        return "kafka:" + topic + "?brokers=" + KAFKA_BROKER;
    }

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        from(topic("warehouseOrderUpdate"))
                .log("Message received from warehouseOrderUpdate topic: ${body}")
                .process((exchange) -> {
                    String body = exchange.getIn().getBody(String.class);
                    WarehouseOrderUpdate warehouseOrderUpdate = WarehouseOrderUpdate.fromJson(body);
                    System.out.println("WarehouseOrderUpdate: " + warehouseOrderUpdate);
                    switch (warehouseOrderUpdate.getEvent()) {
                        case "STORE_ENOUGH_PLANTS":
                            paymentManager.addOrder(warehouseOrderUpdate);
                            break;
                        default:
                            break;
                    }
                });

        from(topic("cancelOrder"))
                .log("Received order to cancel order ${body}")
                .unmarshal().json(CancelOrderRequest.class)
                .process((exchange) -> {
                    CancelOrderRequest cor = exchange.getMessage().getBody(CancelOrderRequest.class);
                    PaymentStatusUpdate psu = new PaymentStatusUpdate(paymentManager.getOrder(cor.getOrderID()));
                    if (paymentManager.getOrder(cor.getOrderID()).getEvent().equals("PAYMENT_SUCCESSFUL")) {
                        System.out.println("Refunding order: " + cor.getOrderID());
                        psu.setEvent("CANCEL_ORDER");
                        exchange.setProperty("refunding", true);
                    } else if (paymentManager.getOrder(cor.getOrderID()).getEvent().equals("STORE_ENOUGH_PLANTS")) {
                        System.out.println("Cancelling order: " + cor.getOrderID());
                        psu.setEvent("CANCEL_ORDER");
                        exchange.setProperty("refunding", false);
                    }
                    exchange.getMessage().setBody(psu);
                })
                .choice()
                .when().simple("${exchangeProperty.refunding} == true").delay(30000).marshal().json()
                .log("Sending message to paymentStatusUpdate topic: ${body}")
                .to(topic("paymentStatusUpdate"));

        from(topic("newPaymentForOrder"))
                .log("Message received from newPaymentForOrder topic: ${body}")
                .unmarshal().json(NewPaymentForOrder.class)
                .process((exchange) -> {
                    NewPaymentForOrder npfo = exchange.getIn().getBody(NewPaymentForOrder.class);
                    System.out.println("NewPaymentForOrder: " + npfo);
                    WarehouseOrderUpdate order = paymentManager.getOrder(npfo.getOrderID());
                    if (order == null) {
                        exchange.setProperty("orderNotFound", true);
                    } else {
                        paymentManager.pay(npfo.getOrderID(), npfo.getPaymentCard());
                        WarehouseOrderUpdate newOrder = paymentManager.getOrder(npfo.getOrderID());
                        PaymentStatusUpdate psu = new PaymentStatusUpdate(newOrder);
                        exchange.getIn().setBody(psu);
                    }

                })
                .choice()
                .when().simple("${exchangeProperty.orderNotFound} == true").to("stream:out")
                .otherwise()
                .marshal().json()
                .to(topic("paymentStatusUpdate")).end();

    }

}
