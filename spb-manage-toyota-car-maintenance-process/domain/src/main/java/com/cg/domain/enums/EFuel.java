package com.cg.domain.enums;

public enum EFuel {
    FUEL_OIL("DẦU"),
    FUEL_GASOLINE("XĂNG"),
    FUEL_GASOLINE_ELECTRIC("XĂNG + ĐIỆN");

    private final String value;

    EFuel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EFuel getEFuelByName(String name) {
        for (EFuel fuel : values()) {
            if (fuel.getValue().equals(name)) {
                return fuel;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
