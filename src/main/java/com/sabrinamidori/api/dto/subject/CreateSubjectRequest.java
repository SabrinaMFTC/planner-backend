package com.sabrinamidori.api.dto.subject;

import java.util.List;

public record CreateSubjectRequest(
        String title,
        String teacher,
        List<CreateScheduleItem> schedules) {
}
