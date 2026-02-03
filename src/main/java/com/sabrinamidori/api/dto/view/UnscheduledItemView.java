package com.sabrinamidori.api.dto.view;

import com.sabrinamidori.api.domain.enums.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record UnscheduledItemView(
        UUID id,
        String description,
        TaskStatus status,
        LocalDateTime dueDateTime
) {}
