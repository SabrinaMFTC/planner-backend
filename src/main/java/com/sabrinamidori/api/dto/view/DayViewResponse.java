package com.sabrinamidori.api.dto.view;

import java.time.LocalDate;
import java.util.List;

public record DayViewResponse(
        LocalDate date,
        List<ScheduledItemView> scheduledItems,     // scheduled tasks + subject blocks
        List<TaskView> unscheduledItems             // plannedDate == date
) {}
