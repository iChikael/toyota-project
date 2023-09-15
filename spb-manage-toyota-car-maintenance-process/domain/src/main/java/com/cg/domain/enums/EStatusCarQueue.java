package com.cg.domain.enums;

public enum EStatusCarQueue {
    STATUS_WAITING("Đang chờ"),
    STATUS_DOING("Đang làm"),
    STATUS_DONE("Hoàn thành");

    private final String value;

    EStatusCarQueue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static EStatusCarQueue getEStatusCarQueueByName(String name) {
        for (EStatusCarQueue eStatusCarQueue : values()) {
            if (eStatusCarQueue.getValue().equals(name)) {
                return eStatusCarQueue;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
