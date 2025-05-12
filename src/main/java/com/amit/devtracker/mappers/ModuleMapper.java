package com.amit.devtracker.mappers;

import com.amit.devtracker.domain.dtos.CreateModuleRequest;
import com.amit.devtracker.domain.dtos.ModuleResponse;
import com.amit.devtracker.domain.entities.Module;
import com.amit.devtracker.domain.entities.Roadmap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModuleMapper {

    private final TaskMapper taskMapper;

    // Converts CreateModuleRequest + parent Roadmap into a Module entity
    public Module toEntity(CreateModuleRequest request, Roadmap roadmap) {
        return Module.builder()
                .title(request.getTitle())
                .youtubeUrl(request.getYoutubeUrl())
                .description(request.getDescription())
                .roadmap(roadmap)
                .build();
    }

    // Converts a Module entity into a DTO with nested task responses
    public ModuleResponse toResponse(Module module) {
        return new ModuleResponse(
                module.getId(),
                module.getTitle(),
                module.getDescription(),
                module.getYoutubeUrl(),
                module.getWatched(),
                module.getUserNotes(),
                taskMapper.toResponseList(
                        Optional.ofNullable(module.getTasks()).orElse(Collections.emptyList())
                )

        );
    }

    // Converts a list of Module entities into a list of response DTOs
    public List<ModuleResponse> toResponseList(List<Module> modules) {
        return modules.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
