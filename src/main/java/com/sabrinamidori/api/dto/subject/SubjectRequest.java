package com.sabrinamidori.api.dto.subject;

import java.util.List;

public record SubjectRequest(String title,
                             String professor,
                             List<ScheduleRequest> schedules) {
}
