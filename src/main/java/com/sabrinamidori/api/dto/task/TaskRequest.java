package com.sabrinamidori.api.dto.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TaskRequest(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDate plannedDate,
        LocalDate dueDate,
        LocalTime dueTime,
        String description,
        String status
) {}
