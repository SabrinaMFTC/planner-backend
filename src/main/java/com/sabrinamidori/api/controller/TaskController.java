package com.sabrinamidori.api.controller;

import com.sabrinamidori.api.dto.task.TaskRequest;
import com.sabrinamidori.api.dto.task.TaskResponse;
import com.sabrinamidori.api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> addTask(@RequestBody TaskRequest request) {
        TaskResponse response = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<?> getTasks(@RequestParam(required = false) LocalDate plannedDate) {
        if (plannedDate != null) {
            return ResponseEntity.ok(taskService.getTasksByDay(plannedDate));
        }

        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable UUID id,
                                                   @RequestBody TaskRequest request) {
        TaskResponse response = taskService.updateTask(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}
