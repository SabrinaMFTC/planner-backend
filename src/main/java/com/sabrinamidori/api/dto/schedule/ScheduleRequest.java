package com.sabrinamidori.api.dto.schedule;

public record ScheduleRequest(
        String weekDay,
        String period
) {}
