package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Query("""
        SELECT t
        FROM Task t
        WHERE t.plannedDate = :plannedDate
    """)
    List<Task> findByPlannedDate(LocalDate plannedDate);
}
