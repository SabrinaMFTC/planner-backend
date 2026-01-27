package com.sabrinamidori.api.dto.task;

import java.time.LocalDate;

public record TaskRequest(String status,
                          String title,
                          String description,
                          LocalDate dueDatetime) {
}
