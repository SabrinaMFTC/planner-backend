package com.sabrinamidori.api.dto.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDate plannedDate,
        LocalDateTime dueDateTime,
        String description,
        String status
) {}
