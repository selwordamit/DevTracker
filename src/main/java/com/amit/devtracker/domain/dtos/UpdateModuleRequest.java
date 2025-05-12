package com.amit.devtracker.domain.dtos;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateModuleRequest {

    private Boolean watched = false;

    private String userNotes;
}
