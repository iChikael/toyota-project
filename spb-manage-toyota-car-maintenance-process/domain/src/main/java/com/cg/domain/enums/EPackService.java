package com.cg.domain.enums;

public enum EPackService {

    MAINTENANCE("BDĐK"),
    REPAIR("GMGĐ"),
    PAIN("DS");

    private final String value;

    EPackService(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EPackService getEPackServiceByName(String name) {
        for (EPackService packService : values()) {
            if (packService.getValue().equals(name)) {
                return packService;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
