package com.sabrinamidori.api.domain.enums;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public enum Period {
    MORNING_1(LocalTime.of(7,30), LocalTime.of(9,10)),  // 07:30 - 09:10
    MORNING_2(LocalTime.of(9,20), LocalTime.of(11,0)),  // 09:20 - 11:00
    EVENING_1(LocalTime.of(19,20), LocalTime.of(21,0)),  // 19:20 - 21:00
    EVENING_2(LocalTime.of(21,10), LocalTime.of(22,50));   // 21:10 - 22:50

    private final LocalTime startTime;
    private final LocalTime endTime;

    Period(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static Period from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Period must not be null or blank");
        }

        try {
            return Period.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid period: " + value);
        }
    }
}
