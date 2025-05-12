package com.amit.devtracker.services;
import com.amit.devtracker.mappers.RoadmapMapper;
import com.amit.devtracker.repositories.ModuleRepository;
import com.amit.devtracker.domain.dtos.CreateRoadmapRequest;
import com.amit.devtracker.domain.dtos.RoadmapResponse;
import com.amit.devtracker.domain.entities.Roadmap;
import com.amit.devtracker.repositories.RoadmapRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class RoadmapServiceImpl implements RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final UserService userService;
    private final RoadmapMapper roadmapMapper;
    private final ModuleRepository moduleRepository;

    @Override
    public RoadmapResponse createRoadmap(CreateRoadmapRequest request, UUID userId) {
        log.info("Attempting to create roadmap '{}' for user {}", request.getTitle(), userId);

        // Prevent creation of duplicate roadmap titles per user (case-insensitive)
        if (roadmapRepository.existsByUserIdAndTitleIgnoreCase(userId, request.getTitle())) {
            log.warn("User {} tried to create duplicate roadmap with title '{}'", userId, request.getTitle());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "You already have a roadmap with this title");
        }

        Roadmap roadmap = roadmapMapper.toEntity(request, userService.getUserById(userId));
        Roadmap saved = roadmapRepository.save(roadmap);

        log.info("Roadmap '{}' created successfully for user {}", saved.getTitle(), userId);

        return roadmapMapper.toResponse(saved);
    }

    @Override
    public List<RoadmapResponse> getUserRoadmaps(UUID userId) {
        List<Roadmap> roadmaps = roadmapRepository.findByUserId(userId);

        return roadmaps.stream()
                .map(roadmapMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteRoadmap(UUID roadmapId, UUID userId) {
        log.warn("User {} requested to delete roadmap with ID {}", userId, roadmapId);

        Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> {
                    log.error("Roadmap with ID {} not found", roadmapId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Roadmap not found");
                });

        // Ensure only the owner of the roadmap can delete it
        if (!roadmap.getUser().getId().equals(userId)) {
            log.error("User {} tried to delete roadmap {} which does not belong to them", userId, roadmapId);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You cannot delete someone else's roadmap"
            );
        }

        roadmapRepository.deleteById(roadmapId);
        log.info("Roadmap with ID {} deleted successfully by user {}", roadmapId, userId);
    }
}
