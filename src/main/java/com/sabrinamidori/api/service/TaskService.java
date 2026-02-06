package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.task.Task;
import com.sabrinamidori.api.domain.enums.TaskStatus;
import com.sabrinamidori.api.domain.enums.TaskType;
import com.sabrinamidori.api.dto.task.TaskRequest;
import com.sabrinamidori.api.dto.task.TaskResponse;
import com.sabrinamidori.api.exception.InvalidTaskScheduleException;
import com.sabrinamidori.api.exception.ResourceNotFoundException;
import com.sabrinamidori.api.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskResponse createTask(TaskRequest data) {
        validateSchedule(data.startDateTime(), data.endDateTime());

        Task task = new Task();
        task.setPlannedDate(data.plannedDate());
        task.setDueDateTime(data.dueDateTime());
        task.setDescription(data.description());
        task.setStatus(TaskStatus.from(data.status()));

        setTimeAndType(task, data);

        Task saved = taskRepository.save(task);
        return toTaskResponse(saved);
    }

    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::toTaskResponse)
                .toList();
    }

    public List<TaskResponse> getTasksByDay(LocalDate date) {
        return taskRepository.findByPlannedDate(date)
                .stream()
                .map(this::toTaskResponse)
                .toList();
    }

    public TaskResponse updateTask(UUID taskId, TaskRequest data) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException((
                        "Task with id " + taskId + " not found"
                )));

        if (data.plannedDate() != null) { task.setPlannedDate(data.plannedDate()); }
        if (data.dueDateTime() != null) { task.setDueDateTime(data.dueDateTime()); }
        if (data.description() != null) { task.setDescription(data.description()); }
        if (data.status() != null) { task.setStatus(TaskStatus.from(data.status())); }

        if (data.hasScheduleInfo()) {
            setTimeAndType(task, data);
        }

        validateSchedule(task.getStartDateTime(), task.getEndDateTime());

        Task saved = taskRepository.save(task);
        return toTaskResponse(saved);
    }

    public void deleteTask(UUID taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Task with id " + taskId + " not found"
                ));

        taskRepository.delete(task);
    }

    private void validateSchedule(TaskRequest data) {
        if (data.startDateTime() == null ^ data.endDateTime() == null) {
            throw new InvalidTaskScheduleException(
                    "startDateTime and endDateTime must both be provided or both be null"
            );
        }

        if (data.startDateTime() != null && !data.startDateTime().isBefore(data.endDateTime())) {
            throw new InvalidTaskScheduleException(
                    "startDateTime must be before endDateTime"
            );
        }
    }

    private void validateSchedule(LocalDateTime start, LocalDateTime end) {
        if (start == null ^ end == null) {
            throw new InvalidTaskScheduleException(
                    "startDateTime and endDateTime must both be provided or both be null"
            );
        }

        if (start != null && !start.isBefore(end)) {
            throw new InvalidTaskScheduleException(
                    "startDateTime must be before endDateTime"
            );
        }
    }

    private void setTimeAndType(Task task, TaskRequest data) {
        if (data.startDateTime() != null && data.endDateTime() != null) {
            task.setStartDateTime(data.startDateTime());
            task.setEndDateTime(data.endDateTime());
            task.setType(TaskType.SCHEDULED);
        } else {
            task.setStartDateTime(null);
            task.setEndDateTime(null);
            task.setType(TaskType.UNSCHEDULED);
        }
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getStartDateTime(),
                task.getEndDateTime(),
                task.getPlannedDate(),
                task.getDueDateTime(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
