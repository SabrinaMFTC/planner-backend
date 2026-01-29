package com.sabrinamidori.api.dto.task;

import com.sabrinamidori.api.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(UUID id,
                           TaskStatus status,
                           String description,
                           LocalDateTime dueDateTime) {
}
