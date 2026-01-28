package com.sabrinamidori.api.dto.subject;

import com.sabrinamidori.api.domain.enums.Period;
import com.sabrinamidori.api.domain.enums.WeekDay;

public record ScheduleResponse(WeekDay weekDay,
                               Period period) {
}
