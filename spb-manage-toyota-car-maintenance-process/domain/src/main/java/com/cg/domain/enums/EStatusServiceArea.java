package com.cg.domain.enums;

public enum EStatusServiceArea {
    STATUS_BUSY("Đầy"),
    STATUS_FREE("Trống");

    private final String value;

    EStatusServiceArea(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EStatusServiceArea getEStatusServiceAreaByName(String name) {
        for (EStatusServiceArea unit : values()) {
            if (unit.getValue().equalsIgnoreCase(name)) {
                return unit;
            }
        }
        throw new IllegalArgumentException("Unknown unit: " + name);
    }
}
