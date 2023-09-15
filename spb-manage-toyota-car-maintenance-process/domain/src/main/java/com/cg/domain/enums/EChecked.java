package com.cg.domain.enums;

public enum EChecked {
    NONE("NONE"),
    DONE("DONE");

    private final String value;

    EChecked(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
