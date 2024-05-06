package org.justynafraczek.plantsshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

import org.justynafraczek.plantsshop.types.ProcessingEvent;

public class PaymentStatusUpdate {
    @JsonProperty("orderID")
    private String orderId;

    @JsonProperty("event")
    private ProcessingEvent event;

    @JsonProperty("totalCost")
    private double totalCost;

    public PaymentStatusUpdate() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ProcessingEvent getEvent() {
        return event;
    }

    public void setEvent(ProcessingEvent event) {
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
