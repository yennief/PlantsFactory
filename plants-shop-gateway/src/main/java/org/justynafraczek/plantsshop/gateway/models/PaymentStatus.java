package org.justynafraczek.plantsshop.gateway.models;

public enum PaymentStatus {
    PENDING("PENDING"),
    PROCESSED("PROCESSED"),
    PAID("PAID"),
    CARD_DECLINED("CARD_DECLINED"),
    PAYMENT_TIMED_OUT("PAYMENT_TIMED_OUT"),
    NO_SUCH_ORDER("NO_SUCH_ORDER"),
    ERROR("ERROR");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
