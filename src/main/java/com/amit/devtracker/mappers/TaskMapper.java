package com.amit.devtracker.mappers;


import com.amit.devtracker.domain.dtos.CreateTaskRequest;
import com.amit.devtracker.domain.dtos.ModuleResponse;
import com.amit.devtracker.domain.dtos.TaskResponse;
import com.amit.devtracker.domain.entities.Task;
import com.amit.devtracker.domain.entities.User;
import com.amit.devtracker.domain.entities.Module;
import com.amit.devtracker.domain.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    // Maps CreateTaskRequest + owning User + Module into Task entity
    public Task toEntity(CreateTaskRequest request, User user, Module module) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .user(user)
                .module(module)
                .status(TaskStatus.TODO)
                .build();
    }

    // Converts Task entity to TaskResponse DTO
    public TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getDueDate()
        );
    }

    // Converts list of Task entities into list of response DTOs
    public List<TaskResponse> toResponseList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
