package com.cg.domain.enums;

public enum EUnit {
    UNIT_WAGE("CÔNG"),
    UNIT_PACKAGE("GÓI"),
    UNIT_PIECE("CÁI"),
    UNIT_SUITE("BỘ"),
    UNIT_LITER("LÍT"),
    UNIT_BOTTLE("CHAI");

    private final String value;

    EUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EUnit getEUnitByName(String name) {
        for (EUnit unit : values()) {
            if (unit.getValue().equalsIgnoreCase(name)) {
                return unit;
            }
        }
        // Handle unknown units by returning a default value or throwing a specific exception.
        // For example:
        // return EUnit.UNKNOWN;
        throw new IllegalArgumentException("Unknown unit: " + name);
    }
}

