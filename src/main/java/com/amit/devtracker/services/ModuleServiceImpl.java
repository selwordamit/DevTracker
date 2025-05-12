package com.amit.devtracker.services;

import com.amit.devtracker.domain.dtos.CreateModuleRequest;
import com.amit.devtracker.domain.dtos.ModuleResponse;
import com.amit.devtracker.domain.dtos.UpdateModuleRequest;
import com.amit.devtracker.domain.entities.Module;
import com.amit.devtracker.domain.entities.Roadmap;
import com.amit.devtracker.mappers.ModuleMapper;
import com.amit.devtracker.repositories.ModuleRepository;
import com.amit.devtracker.repositories.RoadmapRepository;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final RoadmapRepository roadmapRepository;

    @Override
    public ModuleResponse createModule(CreateModuleRequest request) {
        log.info("Attempting to create module '{}' for roadmap {}", request.getTitle(), request.getRoadmapId());

        Roadmap roadmap = roadmapRepository.findById(request.getRoadmapId())
                .orElseThrow(() -> {
                    log.error("Roadmap with ID {} not found", request.getRoadmapId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Roadmap not found");
                });

        Module module = moduleMapper.toEntity(request, roadmap);

        Module saved = moduleRepository.save(module);

        log.info("Module '{}' created successfully for roadmap {}", saved.getTitle(), roadmap.getId());

        return moduleMapper.toResponse(saved);
    }


    @Override
    public List<ModuleResponse> getModulesByRoadmapId(UUID roadmapId) {
        log.info("Fetching modules for roadmap with ID: {}", roadmapId);

        List<Module> modules = moduleRepository.findByRoadmapId(roadmapId);

        modules.forEach(m ->
                log.info("Module raw: id={}, title={}, description={}", m.getId(), m.getTitle(), m.getDescription())
        );

        return moduleMapper.toResponseList(modules);
    }

    public ModuleResponse getModuleById(UUID moduleId) {
        log.info("Fetching module with ID: {}", moduleId);

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> {
                    log.error("Module with ID {} not found", moduleId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found");
                });

        return moduleMapper.toResponse(module);
    }

    @Override
    public ModuleResponse updateModule(UUID moduleId, UpdateModuleRequest request, UUID userId) {
        log.info("Updating module with ID: {}", moduleId);

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> {
                    log.error("Module with ID {} not found", moduleId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Module not found");
                });


        // Ensure that the module belongs to the authenticated user
        if (!module.getRoadmap().getUser().getId().equals(userId)) {
            log.warn("User {} attempted to update module {} which does not belong to them", userId, moduleId);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot update modules that are not yours.");
        }

        // Update only if new values are provided
        if (request.getUserNotes() != null) {
            module.setUserNotes(request.getUserNotes());
        }

        if (request.getWatched() != null) {
            module.setWatched(request.getWatched());
        }

        Module saved = moduleRepository.save(module);

        log.info("Module '{}' updated successfully", saved.getId());

        return moduleMapper.toResponse(saved);
    }

}
