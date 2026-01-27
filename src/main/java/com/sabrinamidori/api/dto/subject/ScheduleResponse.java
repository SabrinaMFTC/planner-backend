package com.sabrinamidori.api.dto.subject;

import com.sabrinamidori.api.domain.enums.WeekDay;

import java.time.LocalTime;

public record ScheduleResponse(WeekDay weekDay,
                               LocalTime startTime,
                               LocalTime endTime) {
}
