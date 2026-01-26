package com.sabrinamidori.api.dto.task;

import java.time.LocalDate;

public record CreateTaskRequest(String status,
                                String title,
                                String description,
                                LocalDate dueDatetime) {
}
