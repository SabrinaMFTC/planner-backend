package com.sabrinamidori.api.dto.subject;

import com.sabrinamidori.api.dto.schedule.ScheduleRequest;

import java.util.List;

public record SubjectRequest(
        String title,
        String professor,
        List<ScheduleRequest> schedules
) {}
