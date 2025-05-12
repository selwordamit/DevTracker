package com.amit.devtracker.controllers;


import com.amit.devtracker.domain.dtos.*;
import com.amit.devtracker.domain.entities.Roadmap;
import com.amit.devtracker.services.ModuleService;
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
// REST controller for managing learning modules within roadmaps
public class ModuleController {

    private final ModuleService moduleService;

    @PostMapping("/modules")
    public ResponseEntity<ModuleResponse> createModule(
            @Valid @RequestBody CreateModuleRequest request
    ) {
        ModuleResponse response = moduleService.createModule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Returns all modules that belong to a specific roadmap
    @GetMapping("/roadmaps/{roadmapId}/modules")
    public ResponseEntity<List<ModuleResponse>> getModulesByRoadmapId(@PathVariable UUID roadmapId) {
        List<ModuleResponse> moduleResponse = moduleService.getModulesByRoadmapId(roadmapId);
        return ResponseEntity.ok(moduleResponse);
    }

    @GetMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> getModuleById(@PathVariable UUID id) {
        ModuleResponse moduleResponse = moduleService.getModuleById(id);
        return ResponseEntity.ok(moduleResponse);
    }

    // Allows the user to update their module's status or notes
    @PutMapping("/modules/{id}")
    public ResponseEntity<ModuleResponse> updateModule(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateModuleRequest request,
            @RequestAttribute("userId") UUID userId // injected from JWT filter
    ) {
        ModuleResponse moduleResponse = moduleService.updateModule(id, request, userId);
        return ResponseEntity.ok(moduleResponse);
    }
}

