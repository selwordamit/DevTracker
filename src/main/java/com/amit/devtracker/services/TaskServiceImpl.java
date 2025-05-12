package com.amit.devtracker.services;


import com.amit.devtracker.domain.dtos.CreateTaskRequest;
import com.amit.devtracker.domain.dtos.TaskResponse;
import com.amit.devtracker.domain.entities.Module;
import com.amit.devtracker.domain.entities.Task;
import com.amit.devtracker.domain.entities.User;
import com.amit.devtracker.mappers.TaskMapper;
import com.amit.devtracker.repositories.ModuleRepository;
import com.amit.devtracker.repositories.TaskRepository;
import com.sun.source.util.TaskListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ModuleRepository moduleRepository;
    private final UserService userService;

    @Override
    public TaskResponse createTask(CreateTaskRequest request, UUID userId) {
        log.info("Attempting to create task '{}' for module {}", request.getTitle(), request.getModuleId());

        Module module = moduleRepository.findById(request.getModuleId())
                .orElseThrow(() -> {
                    log.error("Module with ID {} not found", request.getModuleId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found");
                });

        User user = userService.getUserById(userId);

        // Ensure the module belongs to the authenticated user before creating a task
        if (!module.getRoadmap().getUser().getId().equals(user.getId())) {
            log.warn("User {} attempted to create task in module {} which is not theirs", userId, module.getId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot create tasks in modules that are not yours.");
        }

        Task task = taskMapper.toEntity(request, user, module);
        Task saved = taskRepository.save(task);

        log.info("Task '{}' created successfully under module {}", saved.getTitle(), module.getId());

        return taskMapper.toResponse(saved);
    }


    @Override
    public List<TaskResponse> getTasksByModuleId(UUID moduleId) {
        log.info("Fetching task for module with ID: {}", moduleId);

        List<Task> tasks = taskRepository.findByModuleId(moduleId);

        tasks.forEach(m ->
                log.info("Module raw: id={}, title={}, description={}", m.getId(), m.getTitle(), m.getDescription())
        );

        return taskMapper.toResponseList(tasks);
    }
}
