package com.amit.devtracker.controllers;

import com.amit.devtracker.domain.dtos.CreateTaskRequest;
import com.amit.devtracker.domain.dtos.TaskResponse;
import com.amit.devtracker.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
// REST controller for managing user tasks
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest taskRequest,
            @RequestAttribute("userId") UUID userId) { // Injected from JWT in filter

        TaskResponse response = taskService.createTask(taskRequest,userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // Returns all tasks associated with a specific module
    @GetMapping("/modules/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasksByModuleId(@PathVariable("id") UUID moduleId) {
        List<TaskResponse> taskResponse = taskService.getTasksByModuleId(moduleId);
        return ResponseEntity.ok(taskResponse);
    }

}
