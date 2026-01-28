package com.sabrinamidori.api.domain.enums;

public enum WeekDay {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY;

    public static WeekDay from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Week day must not be null or blank");
        }

        try {
            return WeekDay.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid week day: " + value);
        }
    }
}
