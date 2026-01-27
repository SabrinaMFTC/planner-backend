package com.sabrinamidori.api.service;

import com.sabrinamidori.api.domain.entity.task.Task;
import com.sabrinamidori.api.domain.enums.TaskStatus;
import com.sabrinamidori.api.dto.task.TaskRequest;
import com.sabrinamidori.api.dto.task.TaskResponse;
import com.sabrinamidori.api.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    /* TODO:
        - Create tasks
        - Get tasks
        - Update tasks
            - change duedate
            - change status
        - Delete tasks
            - exclude tasks that you dont want to keep as DONE
    */

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse createTask(TaskRequest data) {
        Task task = new Task();
        task.setTaskStatus(TaskStatus.valueOf(data.status()));
        task.setTitle(data.title());
        task.setDescription(data.description());
        task.setDueDateTime(data.dueDatetime().atStartOfDay());

        Task saved = task.toTaskResponse();
        TaskResponse saved = taskRepository.save(task);

    }
}
