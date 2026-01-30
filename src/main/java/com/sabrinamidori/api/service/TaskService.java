package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.subject.Subject;
import com.sabrinamidori.api.domain.entity.task.Task;
import com.sabrinamidori.api.domain.enums.TaskStatus;
import com.sabrinamidori.api.dto.task.TaskRequest;
import com.sabrinamidori.api.dto.task.TaskResponse;
import com.sabrinamidori.api.exception.ResourceNotFoundException;
import com.sabrinamidori.api.repository.SubjectRepository;
import com.sabrinamidori.api.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest data) {
        Task task = new Task();
        setData(task, data);

        Task saved = taskRepository.save(task);
        return toTaskResponse(saved);
    }

    public List<TaskResponse> getTasks() {

    }

    public TaskResponse updateTask(UUID taskId, TaskRequest data) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException((
                        "Task with id " + taskId + " not found"
                )));

        if (data.status() != null) {
            task.setTaskStatus(TaskStatus.from(data.status()));
        }

        if (data.description() != null) {
            task.setDescription(data.description());
        }

        if (data.dueDateTime() != null) {
            task.setDueDateTime(data.dueDateTime());
        }

        Task saved = taskRepository.save(task);
        return toTaskResponse(saved);
    }

    private void setData(Task task, TaskRequest data) {
        task.setStartTime(data.startTime());
        task.setEndTime(data.endTime());
        task.setDescription(data.description());
        task.setStatus(TaskStatus.from(data.status()));
        task.setDueDateTime(data.dueDateTime());
    }

    private TaskResponse toTaskResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTaskStatus(),
                task.getDescription(),
                task.getDueDateTime()
        );
    }
}
