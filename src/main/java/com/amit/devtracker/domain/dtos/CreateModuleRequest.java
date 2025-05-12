package com.amit.devtracker.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateModuleRequest {

    @NotBlank(message = "Module title is required")
    private String title;

    @NotBlank(message = "YouTube URL is required")
    private String youtubeUrl;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Roadmap ID is required")
    private UUID roadmapId;
}
