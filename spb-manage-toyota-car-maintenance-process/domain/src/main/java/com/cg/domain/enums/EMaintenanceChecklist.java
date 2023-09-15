package com.cg.domain.enums;

public enum EMaintenanceChecklist {
    CHECK("Kiểm tra"),
    TOILET("Vệ sinh"),
    TIGHT("Siếc chặt"),
    REPLACE("Thay thế"),
    NONE("Không");

    private final String value;

    EMaintenanceChecklist(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
    public static EMaintenanceChecklist getEMaintenanceChecklistByName(String name) {
        for (EMaintenanceChecklist eMaintenanceChecklist : values()) {
            if (eMaintenanceChecklist.getValue().equals(name)) {
                return eMaintenanceChecklist;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
