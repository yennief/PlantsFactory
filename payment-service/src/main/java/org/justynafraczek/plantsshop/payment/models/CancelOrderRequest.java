package org.justynafraczek.plantsshop.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CancelOrderRequest {

    @JsonProperty
    private String orderID;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
