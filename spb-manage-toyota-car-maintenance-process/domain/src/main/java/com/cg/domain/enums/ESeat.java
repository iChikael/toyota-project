package com.cg.domain.enums;

public enum ESeat {
    SEAT_FIVE("5"),
    SEAT_SEVEN("7"),
    SEAT_EIGHT("8"),
    SEAT_NINE("9"),
    SEAT_FIFTEEN("15");

    private final String value;

    ESeat(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ESeat getESeatByName(String name) {
        for (ESeat seat : values()) {
            if (seat.getValue().equals(name)) {
                return seat;
            }
        }
        throw new IllegalArgumentException("Please re-enter");
    }
}
