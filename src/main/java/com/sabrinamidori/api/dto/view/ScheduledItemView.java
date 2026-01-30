package com.sabrinamidori.api.dto.view;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScheduledItemView(
        String type,    // task or subject
        String title,   // task description or subject name
        LocalDateTime startTime,
        LocalDateTime endTime,
        UUID refId      // taskId or subjectId
) {}
