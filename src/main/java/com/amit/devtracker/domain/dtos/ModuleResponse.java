package com.amit.devtracker.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuleResponse {
    private UUID id;
    private String videoTitle;
    private String description;
    private String youtubeUrl;
    private Boolean watched;
    private String userNotes;

    private List<TaskResponse> tasks;
}
