package com.sabrinamidori.api.domain.entity.task;

import com.sabrinamidori.api.domain.enums.TaskStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    private TaskStatus taskStatus;

    @Column(nullable = false)
    private String description;

    @Column(name = "due_date_time", nullable = false)
    private LocalDateTime dueDateTime;
}
