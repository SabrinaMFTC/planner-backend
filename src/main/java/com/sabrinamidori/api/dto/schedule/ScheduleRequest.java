package com.sabrinamidori.api.dto.schedule;

import com.sabrinamidori.api.domain.enums.Period;

import java.time.DayOfWeek;

public record ScheduleRequest(
        DayOfWeek weekDay,
        Period period
) {}
