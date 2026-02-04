package com.sabrinamidori.api.dto.task;

import com.sabrinamidori.api.domain.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDate plannedDate,
        LocalDate dueDate,
        LocalTime dueTime,
        String description,
        TaskStatus status
) {}
