package com.sabrinamidori.api.controller;

import com.sabrinamidori.api.dto.task.TaskRequest;
import com.sabrinamidori.api.dto.task.TaskResponse;
import com.sabrinamidori.api.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("subjects/{subjectId}")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> addTask(@PathVariable UUID subjectId,
                                                @RequestBody TaskRequest task) {
        TaskResponse response = taskService.createTask(subjectId, task);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable UUID subjectId) {
        List<TaskResponse> response = taskService.getTasksBySubject(subjectId);

        return ResponseEntity.ok(response);
    }

//    @PatchMapping()


    // updateTask
    // deleteTask
}
