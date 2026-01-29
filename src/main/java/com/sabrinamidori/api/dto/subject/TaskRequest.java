package com.sabrinamidori.api.dto.subject;

import java.time.LocalDateTime;

public record TaskRequest(String subject,
                          String status,
                          String description,
                          LocalDateTime dueDatetime) {
}
