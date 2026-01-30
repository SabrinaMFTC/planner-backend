package com.sabrinamidori.api.dto.task;

import java.time.LocalDateTime;

public record TaskRequest(
        LocalDateTime startTime,
        LocalDateTime endTime,
        LocalDateTime dueDateTime,
        String description,
        String status
) {}
