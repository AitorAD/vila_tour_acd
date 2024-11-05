package com.example.vila_tour.domain;

public enum Role {
    USER,
    ADMIN,
    EDITOR;

    public static boolean isStringInEnum(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}