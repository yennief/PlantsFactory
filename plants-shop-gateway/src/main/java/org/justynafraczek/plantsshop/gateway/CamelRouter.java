/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.justynafraczek.plantsshop.gateway;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

import java.math.BigDecimal;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JacksonXMLDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
// import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;
import org.justynafraczek.plantsshop.gateway.models.GateException;
import org.justynafraczek.plantsshop.gateway.models.GetOrdersResponse;
import org.justynafraczek.plantsshop.gateway.models.OrderInfo;
import org.justynafraczek.plantsshop.PlantsOrderRequest;
import org.justynafraczek.plantsshop.CancelOrderRequest;
import org.justynafraczek.plantsshop.CancelOrderResponse;
import org.justynafraczek.plantsshop.GetPlantsRequest;
import org.justynafraczek.plantsshop.GetPlantsResponse;
import org.justynafraczek.plantsshop.NewPlantsOrderResponse;
import org.justynafraczek.plantsshop.gateway.models.PaymentRequest;
import org.justynafraczek.plantsshop.gateway.models.PaymentStatus;
import org.justynafraczek.plantsshop.gateway.models.PaymentStatusUpdate;
import org.justynafraczek.plantsshop.types.PlantsOrder;
import org.justynafraczek.plantsshop.gateway.models.soap.CancelPlantsOrder;
import org.justynafraczek.plantsshop.gateway.models.PaymentImmediateResponse;
import org.justynafraczek.plantsshop.gateway.models.OrderRequest;
import org.justynafraczek.plantsshop.types.ProcessingEvent;
import org.justynafraczek.plantsshop.types.ProcessingState;
import org.justynafraczek.plantsshop.gateway.state.StateService;
import org.justynafraczek.plantsshop.warehouse.PlantsWarehouse;
import org.justynafraczek.plantsshop.warehouse.PlantsWarehouseService;
import org.springframework.stereotype.Component;

import io.swagger.util.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * A simple Camel REST DSL route.
 */
@Component
public class CamelRouter extends RouteBuilder {

    @org.springframework.beans.factory.annotation.Autowired
    OrdersService ordersService;

    final String plantsOrderUrl = "http://"+System.getenv("SOAP_API_HOST")+"/soap-api/service/plantswarehouse";

    @Value("${KAFKA_BROKER_URL}")
    private String kafkaBrokerURL;

    @Bean
    public CamelSagaService getsaga() {
        return new InMemorySagaService();
    }

    @Autowired
    StateService ordersStateService;

    @Override
    public void configure() throws Exception {
        gateway();
        orders();
        payments();
        plants();
    }

    private void gateway() {
        restConfiguration().component("servlet").bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true").enableCORS(true).corsAllowCredentials(true)
                .contextPath("/api").apiContextPath("/api-doc").apiProperty("port", "8080")
                .apiContextRouteId("api-doc");

        // Orders resource.
        // POST /orders - place a new order
        // GET  /orders/{orderId} - get order details
        rest("/orders").description("Order plants")
                .post().description("Place a new order").type(OrderRequest.class)
                .consumes("application/json").produces("application/json").outType(NewPlantsOrderResponse.class)
                .param().name("body").type(body).description("Body of the request").endParam()
                .responseMessage().code(200).message("Plants order placed successfully").endResponseMessage()
                .responseMessage().code(400).responseModel(GateException.class).message("Post order exception")
                .endResponseMessage()
                .to("direct:newOrder")
                .get().description("Get all orders")
                .produces("application/json").outType(GetOrdersResponse.class)
                .to("direct:getOrders")
                .get("/{orderId}")
                .param().name("orderId").type(path).description("Order ID").endParam()
                .produces("application/json").outType(PlantsOrder.class)
                .to("direct:getOrder")
                .get("/{orderId}/cancel").param().name("orderId").type(path).description("ID of the order to cancel").endParam()
                .produces("application/json").outType(CancelOrderResponse.class)
                .to("direct:cancelOrder");

        // Plants resource.
        // GET /plants - get a list of all plants
        rest("/plants").description("Plants endpoint")
            .get().description("Get a list of all plants")
            .produces("application/json").outType(GetPlantsResponse.class)
            .to("direct:getPlants");

        // Payments resource.
        // POST /payments/pay - pay for the order
        rest("/payments").description("Finalize payment")
                .post("/pay").description("Pay for the order")
                .type(PaymentRequest.class).consumes("application/json").produces("application/json")
                .outType(PaymentImmediateResponse.class)
                .param().name("body").type(body).description("Payment request body").endParam()
                .to("direct:payment");
    }

    private void plants() {
        final JaxbDataFormat jaxbGetPlantsRequest = new JaxbDataFormat(GetPlantsRequest.class.getPackage().getName());
        final JaxbDataFormat jaxbGetPlantsResponse = new JaxbDataFormat(GetPlantsResponse.class.getPackage().getName());

        // TODO: Implement /get plant

        from("direct:getPlants").routeId("getPlants").log("Getting all plants.")
            .process((exchange) -> {
                GetPlantsRequest gpr = new GetPlantsRequest();
                exchange.getMessage().setBody(gpr);
            }).marshal(jaxbGetPlantsRequest)
            .to("spring-ws:" + plantsOrderUrl + "/getPlants")
            .unmarshal(jaxbGetPlantsResponse)
            .marshal().json(JsonLibrary.Jackson).unmarshal().json()
            .log("After returning response: ${body}");

    }

    private void payments() {

        from("kafka:paymentStatusUpdate?brokers="+kafkaBrokerURL)
            .routeId("paymentStatusUpdate").log("paymentStatusUpdate fired ${body}")
            .unmarshal().json(JsonLibrary.Jackson, PaymentStatusUpdate.class)
            .process((exchange) -> {
                PaymentStatusUpdate paymentStatusUpdate = exchange.getMessage().getBody(PaymentStatusUpdate.class);
                System.out.println("Payment response: " + paymentStatusUpdate);
                String orderId = paymentStatusUpdate.getOrderId();
                ProcessingState new_state = ordersStateService.sendEvent(orderId, paymentStatusUpdate.getEvent());
                ordersService.setOrderStatus(orderId, new_state);
            })
            .marshal().json()
            .log("After returning response: ${body}");


        from("direct:payment").routeId("payment").log("payment request fired")
            .marshal().json()
            .to("kafka:newPaymentForOrder?brokers="+kafkaBrokerURL)
            .unmarshal().json(JsonLibrary.Jackson, PaymentRequest.class)
            .process((exchange) -> {
                PaymentRequest paymentRequest = exchange.getMessage().getBody(PaymentRequest.class);
                PaymentImmediateResponse paymentImmediateResponse = new PaymentImmediateResponse();

                String orderId = paymentRequest.getOrderId();
                PlantsOrder po =  ordersService.getOrder(orderId);
                if (po == null) {
                    paymentImmediateResponse.setPaymentStatus(PaymentStatus.NO_SUCH_ORDER);
                    paymentImmediateResponse.setMessage("There is no such order in the database.");
                } else if (po.getStatus() != ProcessingState.AWAITING_PAYMENT) {
                    paymentImmediateResponse.setPaymentStatus(PaymentStatus.ERROR);
                    paymentImmediateResponse.setMessage("You can't pay for this order.");
                 } else {
                    paymentImmediateResponse.setPaymentStatus(PaymentStatus.PENDING);
                    paymentImmediateResponse.setMessage("Payment is being processed.");
                }
                exchange.getMessage().setBody(paymentImmediateResponse);
            });

    }

    private void orders() {

        final JaxbDataFormat jaxbOrderPlantsResponse = new JaxbDataFormat(
                NewPlantsOrderResponse.class.getPackage().getName());

        final JaxbDataFormat jaxbOrderPlantsRequestToWS = new JaxbDataFormat(PlantsOrderRequest.class.getPackage().getName());

        final JaxbDataFormat jaxbNewOrderResponse = new JaxbDataFormat(NewPlantsOrderResponse.class.getPackage().getName());
        final JaxbDataFormat jaxbCancelOrderRequest = new JaxbDataFormat(CancelOrderRequest.class.getPackage().getName());
        final JaxbDataFormat jaxbCancelOrderResponse = new JaxbDataFormat(CancelOrderResponse.class.getPackage().getName());

        from("direct:getOrders").routeId("getOrders").log("Listing all orders")
            .process((exchange) -> {
                GetOrdersResponse getOrdersResponse = new GetOrdersResponse();
                getOrdersResponse.setOrders(ordersService.getAllOrders());
                exchange.getMessage().setBody(getOrdersResponse);
            });

        from("direct:newOrder").routeId("newOrder").log("New order is being placed...")
            .process((exchange) -> {
                OrderRequest orderRequest = exchange.getMessage().getBody(OrderRequest.class);
                System.out.println(orderRequest);
                String newID = ordersService.createNewOrder(orderRequest);

                ordersStateService.sendEvent(newID, ProcessingEvent.START);
                
                PlantsOrderRequest opr = new PlantsOrderRequest();
                opr.setPlantsOrder(ordersService.getOrder(newID));

                exchange.getMessage().setBody(opr);
            })
            .log("After: ${body}")
            .marshal(jaxbOrderPlantsRequestToWS)
            .to("spring-ws:" + plantsOrderUrl + "/newOrder")
            .unmarshal(jaxbNewOrderResponse)
            .process((exchange) -> {
                System.out.println("Body:" + exchange.getMessage().getBody());

                NewPlantsOrderResponse newPlantsOrderResponse = exchange.getMessage().getBody(NewPlantsOrderResponse.class);

                ProcessingState new_state = ordersStateService.sendEvent(newPlantsOrderResponse.getOrderID(), newPlantsOrderResponse.getEvent());

                ordersService.setOrderStatus(newPlantsOrderResponse.getOrderID(), new_state);
                ordersService.setOrderTotalCost(newPlantsOrderResponse.getOrderID(), newPlantsOrderResponse.getTotalCost());

                System.out.println("Status: " + newPlantsOrderResponse.getTotalCost() + newPlantsOrderResponse.getEvent());
            })
            .log("After returning response: ${body}")
            .marshal().json()
            .unmarshal().json();

        from("direct:getOrder")
            .routeId("getOrder").log("getOrder fired").process(exchange -> {

                String orderId = exchange.getMessage().getHeader("orderId", String.class);

                PlantsOrder found = ordersService.getOrder(orderId);

                exchange.getMessage().setBody(found);
            })
            .marshal().json()
            .unmarshal().json()
            .log("Found order: ${body}");

        from("kafka:warehouseOrderUpdate?brokers="+kafkaBrokerURL)
            .log("warehouseOrderUpdate fired ${body}")
            .unmarshal().json(NewPlantsOrderResponse.class)
            .process((exchange) -> {
                NewPlantsOrderResponse newPlantsOrderResponse = exchange.getMessage().getBody(NewPlantsOrderResponse.class);
                System.out.println("Body:" + newPlantsOrderResponse);
                //complete jest ustawiane w dispacher
                if (newPlantsOrderResponse.getEvent() == ProcessingEvent.COMPLETE) {
                    System.out.println("Order " + newPlantsOrderResponse.getOrderID() + " has been completed");
                    //complete jest wysylany do maszyny stanow
                    ProcessingState new_state = ordersStateService.sendEvent(newPlantsOrderResponse.getOrderID(), newPlantsOrderResponse.getEvent());
                    ordersService.setOrderStatus(newPlantsOrderResponse.getOrderID(), new_state);
                }
            }).end();

        from("direct:cancelOrder")
                .routeId("cancelPlantsOrder").log("cancelOrder fired")
                .process(exchange -> {
                    String orderId = exchange.getMessage().getHeader("orderId", String.class);

                    ProcessingState new_state = ordersStateService.sendEvent(orderId, ProcessingEvent.CANCEL_ORDER);
                    ordersService.setOrderStatus(orderId, new_state);
                    System.out.println("New state: " + new_state);
                    CancelOrderRequest cancelPlantsOrder = new CancelOrderRequest();
                    cancelPlantsOrder.setOrderID(orderId);
                    exchange.getMessage().setBody(cancelPlantsOrder);
                })
                .marshal().json().to("kafka:cancelOrder?brokers="+kafkaBrokerURL)
                .unmarshal().json(JsonLibrary.Jackson, CancelOrderRequest.class)
                .marshal(jaxbCancelOrderRequest)
                .to("spring-ws:" + plantsOrderUrl + "/cancelOrder")
                .unmarshal(jaxbCancelOrderResponse).marshal().json().unmarshal().json();


    }

}
