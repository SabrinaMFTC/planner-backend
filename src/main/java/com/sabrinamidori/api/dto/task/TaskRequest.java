package com.sabrinamidori.api.dto.task;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskRequest(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        LocalDate plannedDate,
        LocalDateTime dueDateTime,
        String description,
        String status
) {
    public boolean hasScheduleInfo() {
        return startDateTime != null || endDateTime != null;
    }
}
