package com.example.BookYourJam.user.enums;


public enum UserRole {
    CUSTOMER(1),
    OWNER(2);

    private final int value;

    UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
