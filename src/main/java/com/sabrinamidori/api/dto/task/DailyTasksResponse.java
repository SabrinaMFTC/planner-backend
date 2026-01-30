package com.sabrinamidori.api.dto.task;

import com.sabrinamidori.api.domain.entity.task.Task;

import java.time.LocalDate;
import java.util.List;

public record DailyTasksResponse(
        List<Task> scheduledTasks,
        List<Task> unscheduledTasks,
        LocalDate date
) {}
