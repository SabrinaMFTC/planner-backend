package com.sabrinamidori.api.dto.subject;

import java.time.LocalTime;

public record ScheduleRequest(String weekDay,
                              LocalTime startTime,
                              LocalTime endTime) {
}
