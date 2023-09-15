package com.cg.domain.enums;

public enum EPayment {

    PAYMENT_INTERNAL("NB"),
    PAYMENT_CUSTOMER("KHTT");
    private final String value;

    EPayment(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EPayment getEPaymentByName(String name) {
        for (EPayment payment : values()) {
            if (payment.getValue().equals(name)) {
                return payment;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
