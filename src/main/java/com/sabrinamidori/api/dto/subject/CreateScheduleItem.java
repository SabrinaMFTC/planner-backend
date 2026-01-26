package com.sabrinamidori.api.dto.subject;

import java.time.LocalTime;

public record CreateScheduleItem(
        String weekDay,
        LocalTime startTime,
        LocalTime endTime) {
}
