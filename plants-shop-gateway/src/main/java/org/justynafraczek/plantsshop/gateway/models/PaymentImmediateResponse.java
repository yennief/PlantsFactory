package org.justynafraczek.plantsshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PaymentImmediateResponse {

    @JsonProperty("payment_status")
    private PaymentStatus paymentStatus;

    @JsonProperty("message")
    private String message;

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    // JSON serialization/deserialization methods
    public String toJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    public static PaymentImmediateResponse fromJson(String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, PaymentImmediateResponse.class);
    }
}
