package com.sabrinamidori.api.dto.subject;

import java.time.LocalDate;

public record TaskRequest(String status,
                          String title,
                          String description,
                          LocalDate dueDatetime) {
}
