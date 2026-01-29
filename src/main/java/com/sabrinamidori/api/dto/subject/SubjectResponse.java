package com.sabrinamidori.api.dto.subject;

import com.sabrinamidori.api.dto.schedule.ScheduleResponse;

import java.util.List;
import java.util.UUID;

public record SubjectResponse(UUID id,
                              String title,
                              String professor,
                              List<ScheduleResponse> schedules) {
}
