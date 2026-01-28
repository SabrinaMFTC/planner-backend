package com.sabrinamidori.api.dto.subject;

import com.sabrinamidori.api.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(UUID id,
                           TaskStatus status,
                           String title,
                           String description,
                           LocalDateTime dueDateTime) {
}
