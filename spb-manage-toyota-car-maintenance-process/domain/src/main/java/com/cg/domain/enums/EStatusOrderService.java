package com.cg.domain.enums;

public enum EStatusOrderService {
    STATUS_WAITING_PAYMENT("Chờ thanh toán"),
    STATUS_DONE("Đã thanh toán");

    private final String value;

    EStatusOrderService(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
