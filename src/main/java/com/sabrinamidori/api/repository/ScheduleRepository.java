package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.schedule.Schedule;
import com.sabrinamidori.api.domain.enums.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, UUID> {
    @Query("""
        SELECT COUNT(s) > 0
        FROM Schedule s
        WHERE s.weekDay = :weekDay
        AND s.period = :period
    """)
    boolean existsByWeekDayAndPeriod(
            @Param("weekDay") DayOfWeek weekDay,
            @Param("period") Period period
    );

    @Query("""
        SELECT COUNT(s) > 0
        FROM Schedule s
        WHERE s.weekDay = :weekDay
        AND s.period = :period
        AND s.subject.id <> :subjectId
    """)
    boolean existsByWeekDayAndPeriodAndSubjectNot(
            @Param("weekDay") DayOfWeek weekDay,
            @Param("period") Period period,
            @Param("subjectId") UUID subjectId
    );
}
