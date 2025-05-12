package com.amit.devtracker.controllers;


import com.amit.devtracker.domain.dtos.AuthResponse;
import com.amit.devtracker.domain.dtos.LoginRequest;
import com.amit.devtracker.domain.dtos.RegisterRequest;
import com.amit.devtracker.services.AuthService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
// REST controller for authentication endpoints (register & login)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid  @RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse authResponse = (authService.register(registerRequest));
            return ResponseEntity.ok(authResponse);

        } catch (EntityExistsException e) {
            // Email already exists in DB
            return ResponseEntity.badRequest().body("Email already in use.");
        }
    }




    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = (authService.login(loginRequest));
            return ResponseEntity.ok(authResponse);

        } catch (UsernameNotFoundException e) {
            // No user found with the given email
            return ResponseEntity.badRequest().body("User not found.");
        }

    }

}
