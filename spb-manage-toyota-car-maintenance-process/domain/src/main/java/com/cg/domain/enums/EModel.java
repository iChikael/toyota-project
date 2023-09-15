package com.cg.domain.enums;

public enum EModel {
    MODEL_SEDAN("SEDAN"),
    MODEL_HATCHBACK("HATCHBACK"),
    MODEL_SUV("SUV"),
    MODEL_MPV("ĐA DỤNG"),
    MODEL_PICKUP_TRUCK("BÁN TẢI");

    private final String value;

    EModel(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EModel getEModelByName(String name) {
        for (EModel model : values()) {
            if (model.getValue().equals(name)) {
                return model;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
