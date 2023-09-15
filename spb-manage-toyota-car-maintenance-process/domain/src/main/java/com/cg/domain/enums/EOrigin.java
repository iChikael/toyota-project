package com.cg.domain.enums;

public enum EOrigin {
    ORIGIN_VN("LẮP RÁP TẠI VN"),
    ORIGIN_IMPORT("XE NHẬP KHẨU");

    private final String value;

    EOrigin(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EOrigin getEOriginByName(String name) {
        for (EOrigin origin : values()) {
            if (origin.getValue().equals(name)) {
                return origin;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
