package com.amit.devtracker.services;

import com.amit.devtracker.domain.dtos.*;

import java.util.List;
import java.util.UUID;

public interface ModuleService {
    ModuleResponse createModule(CreateModuleRequest request);

    List<ModuleResponse> getModulesByRoadmapId(UUID roadmapId);

    ModuleResponse getModuleById(UUID moduleId);

    public ModuleResponse updateModule(UUID moduleId, UpdateModuleRequest request, UUID userId);

}
