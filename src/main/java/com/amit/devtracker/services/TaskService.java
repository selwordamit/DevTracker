package com.amit.devtracker.services;

import com.amit.devtracker.domain.dtos.CreateTaskRequest;
import com.amit.devtracker.domain.dtos.TaskResponse;

import java.util.List;
import java.util.UUID;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request, UUID userId);
    List<TaskResponse> getTasksByModuleId(UUID moduleId);


}
