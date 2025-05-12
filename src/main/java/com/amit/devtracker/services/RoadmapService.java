package com.amit.devtracker.services;


import com.amit.devtracker.domain.dtos.CreateRoadmapRequest;
import com.amit.devtracker.domain.dtos.RoadmapResponse;
import com.amit.devtracker.domain.entities.Roadmap;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface RoadmapService {
    RoadmapResponse createRoadmap(CreateRoadmapRequest request, UUID userId);

    List<RoadmapResponse> getUserRoadmaps(UUID userId);

    void deleteRoadmap(UUID roadmapId, UUID userId);
}
