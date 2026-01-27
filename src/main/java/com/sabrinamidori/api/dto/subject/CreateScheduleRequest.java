package com.sabrinamidori.api.dto.subject;

import java.time.LocalTime;

public record CreateScheduleRequest(String weekDay,
                                    LocalTime startTime,
                                    LocalTime endTime) {
}
