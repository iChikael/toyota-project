package com.cg.domain.enums;

public enum EAccessoryType {
    TYPE_PTDC("PHỤ TÙNG ĐỘNG CƠ"),
    TYPE_PTGX("PHỤ TÙNG GẦM"),
    TYPE_PTTV("PHỤ TÙNG THÂN - VỎ"),
    TYPE_PTDDH("PHỤ TÙNG ĐIỆN - ĐIỀU HÒA"),
    TYPE_PTK("PHỤ TÙNG KHÁC");

    private final String value;

    EAccessoryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EAccessoryType getEAccessoryTypeByName(String name) {
        for (EAccessoryType accessoryType : values()) {
            if (accessoryType.getValue().equals(name)) {
                return accessoryType;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
