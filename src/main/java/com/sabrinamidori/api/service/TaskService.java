package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.task.Task;
import com.sabrinamidori.api.domain.enums.TaskStatus;
import com.sabrinamidori.api.dto.task.TaskRequest;
import com.sabrinamidori.api.dto.task.TaskResponse;
import com.sabrinamidori.api.exception.InvalidTaskScheduleException;
import com.sabrinamidori.api.exception.ResourceNotFoundException;
import com.sabrinamidori.api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest data) {
        validateSchedule(data);

        Task task = new Task();
        setData(task, data);

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

        setData(task, data);
        validateSchedule(data);

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
        boolean scheduled =
                data.startTime() != null &&
                data.endTime() != null &&
                data.startTime().isBefore(data.endTime());

        boolean unscheduled =
                data.startTime() == null &&
                data.endTime() == null &&
                data.plannedDate() != null;

        if (!(scheduled || unscheduled)) {
            throw new InvalidTaskScheduleException(
                "Task must be either scheduled (start time and end time) or unscheduled with planned date"
            );
        }
    }

    private void setData(Task task, TaskRequest data) {
        if (data.startTime() != null) { task.setStartTime(data.startTime()); }
        if (data.endTime() != null) { task.setEndTime(data.endTime()); }
        if (data.plannedDate() != null) { task.setPlannedDate(data.plannedDate()); }
        if (data.dueDateTime() != null) { task.setDueDateTime(data.dueDateTime()); }
        if (data.description() != null) { task.setDescription(data.description()); }
        if (data.status() != null) { task.setStatus(TaskStatus.from(data.status())); }
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getStartTime(),
                task.getEndTime(),
                task.getPlannedDate(),
                task.getDueDateTime(),
                task.getDescription(),
                task.getStatus()
        );
    }
}
