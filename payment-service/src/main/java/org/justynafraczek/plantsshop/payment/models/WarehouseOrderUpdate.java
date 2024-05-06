package org.justynafraczek.plantsshop.payment.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WarehouseOrderUpdate {
    @JsonProperty("orderID")
    private String orderId;

    @JsonProperty("event")
    private String event;

    @JsonProperty("totalCost")
    private double totalCost;

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

    public static WarehouseOrderUpdate fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, WarehouseOrderUpdate.class);
    }

    @Override
    public String toString() {
        return "WarehouseOrderUpdate{" +
                "orderId='" + orderId + '\'' +
                ", event='" + event + '\'' +
                ", totalCost=" + totalCost +
                '}';
    }
}
