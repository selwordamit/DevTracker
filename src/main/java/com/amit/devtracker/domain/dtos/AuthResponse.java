package com.amit.devtracker.domain.dtos;


import com.amit.devtracker.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
// DTO returned after successful authentication (login/register)
public class AuthResponse {

    private String token;
    private String fullName;
    private Role role; // User's role (e.g., USER, ADMIN)
}
