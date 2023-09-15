package com.cg.domain.enums;

public enum EStatusOrderServiceDetail {
    STATUS_WAITING("Đang chờ"),
    STATUS_DOING("Đang làm"),
    STATUS_DONE("Hoàn thành");

    private final String value;

    EStatusOrderServiceDetail(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EStatusOrderServiceDetail getEStatusOrderServiceDetailByName(String name) {
        for (EStatusOrderServiceDetail eStatusOrderServiceDetail : values()) {
            if (eStatusOrderServiceDetail.getValue().equalsIgnoreCase(name)) {
                return eStatusOrderServiceDetail;
            }
        }
        // Handle unknown units by returning a default value or throwing a specific exception.
        // For example:
        // return EUnit.UNKNOWN;
        throw new IllegalArgumentException("Unknown unit: " + name);
    }
}
