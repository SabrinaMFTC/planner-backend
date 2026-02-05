package com.sabrinamidori.api.dto.task;

import com.sabrinamidori.api.domain.enums.TaskStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDate plannedDate,
        LocalDateTime dueDateTime,
        String description,
        TaskStatus status
) {}
