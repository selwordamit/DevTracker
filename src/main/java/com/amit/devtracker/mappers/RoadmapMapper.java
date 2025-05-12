package com.amit.devtracker.mappers;

import com.amit.devtracker.domain.dtos.CreateRoadmapRequest;
import com.amit.devtracker.domain.dtos.RoadmapResponse;
import com.amit.devtracker.domain.entities.Roadmap;
import com.amit.devtracker.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoadmapMapper {

    private final ModuleMapper moduleMapper;

    // Maps CreateRoadmapRequest + User into a Roadmap entity
    public Roadmap toEntity(CreateRoadmapRequest request, User user) {
        return Roadmap.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();
    }

    // Maps a Roadmap entity to a response DTO, including its modules
    public RoadmapResponse toResponse(Roadmap roadmap) {
        return RoadmapResponse.builder()
                .id(roadmap.getId())
                .title(roadmap.getTitle())
                .description(roadmap.getDescription())
                .modules(
                        Optional.ofNullable(roadmap.getModules())
                                .map(moduleMapper::toResponseList)
                                .orElse(new ArrayList<>())
                )
                .build();
    }
}
