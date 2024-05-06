package org.justynafraczek.plantsshop.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NewPaymentForOrder {

    @JsonProperty("orderId")
    private String orderID;

    @JsonProperty("paymentCard")
    private PaymentCard paymentCard;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public PaymentCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(PaymentCard paymentCard) {
        this.paymentCard = paymentCard;
    }
}
