package com.sabrinamidori.api.dto.task;

import java.time.LocalDateTime;

public record TaskRequest(String status,
                          String description,
                          LocalDateTime dueDateTime) {
}
