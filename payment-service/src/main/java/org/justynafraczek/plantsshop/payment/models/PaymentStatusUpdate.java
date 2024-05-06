package org.justynafraczek.plantsshop.payment.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PaymentStatusUpdate {
    @JsonProperty("orderID")
    private String orderId;

    @JsonProperty("event")
    private String event;

    @JsonProperty("totalCost")
    private double totalCost;

    public PaymentStatusUpdate() {
    }

    public PaymentStatusUpdate(String orderId, String newStatus, String reason) {
        this.orderId = orderId;
    }

    public PaymentStatusUpdate(WarehouseOrderUpdate wou) {
        this.orderId = wou.getOrderId();
        this.event = wou.getEvent();
        this.totalCost = wou.getTotalCost();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String toJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static PaymentStatusUpdate fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, PaymentStatusUpdate.class);
    }
}
