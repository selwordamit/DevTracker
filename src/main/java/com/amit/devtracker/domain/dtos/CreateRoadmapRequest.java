package com.amit.devtracker.domain.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoadmapRequest {

    @NotBlank(message = "Roadmap name is required ")
    @Size(max = 100)
    private String title;
    @Size(max = 500)
    private String description;
}
