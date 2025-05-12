package com.amit.devtracker.controllers;


import com.amit.devtracker.domain.dtos.CreateRoadmapRequest;
import com.amit.devtracker.domain.dtos.RoadmapResponse;
import com.amit.devtracker.security.JwtService;
import com.amit.devtracker.services.RoadmapService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/roadmaps")
// REST controller for managing user-created roadmaps
public class RoadmapController {

    private final RoadmapService roadmapService;
    private final JwtService jwtService;

    @PostMapping
    public ResponseEntity<RoadmapResponse> createRoadmap(
            @Valid @RequestBody CreateRoadmapRequest request,
            @RequestAttribute("userId") UUID userId // Extracted from JwtAuthenticationFilter
    ) {

        RoadmapResponse response = roadmapService.createRoadmap(request, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RoadmapResponse>> getUserRoadmaps(@RequestAttribute("userId") UUID userId) {

        List<RoadmapResponse> responses = roadmapService.getUserRoadmaps(userId);

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteRoadmap(
            @PathVariable UUID id,
            @RequestAttribute("userId") UUID userId // Injected by filter for authenticated requests
    ) {
        roadmapService.deleteRoadmap(id, userId);

        return ResponseEntity.noContent().build();
    }
}

