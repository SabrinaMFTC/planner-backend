package com.sabrinamidori.api.domain.enums;

public enum TaskStatus {
    TODO,
    DOING,
    DONE;

    public static TaskStatus from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Status must not be null or blank");
        }

        try {
            return TaskStatus.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + value);
        }
    }
}
