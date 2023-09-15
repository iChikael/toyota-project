package com.cg.domain.enums;


public enum EUserRole {
    ROLE_ADMIN("ADMIN"),
    ROLE_MANAGER("MANAGER"),
    ROLE_CASHIER("CASHIER"),
    ROLE_RECEPTION("RECEPTION"),
    ROLE_TECHNICAL("TECHNICAL"),
    ROLE_WAREHOUSE("WAREHOUSE"),
    ROLE_CUSTOMER("CUSTOMER");

    private final String value;

    EUserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EUserRole getEUserRoleByName(String name) {
        for (EUserRole eUserRole : values()) {
            if (eUserRole.getValue().equals(name)) {
                return eUserRole;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }

}
