package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    // Scheduled tasks that overlap the day window
    @Query("""
        SELECT t
        FROM Task t
        WHERE t.startTime IS NOT NULL
          AND t.endTime IS NOT NULL
          AND t.startTime < :dayEnd
          AND t.endTime > :dayStart
    """)
    List<Task> findScheduledTasks(
            @Param("dayStart") LocalDateTime dayStart,
            @Param("dayEnd") LocalDateTime dayEnd
    );

    // Unscheduled tasks planned for that day
    List<Task> findUnscheduledTasks(LocalDate plannedDate);
}
