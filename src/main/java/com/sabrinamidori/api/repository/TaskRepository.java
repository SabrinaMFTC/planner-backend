package com.sabrinamidori.api.repository;

import com.sabrinamidori.api.domain.entity.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
