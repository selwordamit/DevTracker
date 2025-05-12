package com.amit.devtracker.services;


import com.amit.devtracker.domain.dtos.AuthResponse;
import com.amit.devtracker.domain.dtos.LoginRequest;
import com.amit.devtracker.domain.dtos.RegisterRequest;
import com.amit.devtracker.domain.entities.User;
import com.amit.devtracker.domain.enums.Role;
import com.amit.devtracker.repositories.UserRepository;
import com.amit.devtracker.security.JwtService;
import com.amit.devtracker.security.UserDetailsImpl;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
// Service implementation for handling user registration and login
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EntityExistsException("Email is already in use");
        }
        // Build new user entity from request and encode password
        User user = User.builder()
                .email(request.getEmail())
                .fullName(request.getFullName())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdAt(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(user);
        // Generate JWT token for the newly registered user
        String token = jwtService.generateToken(new UserDetailsImpl(user));

        return AuthResponse.builder()
                .token(token)
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }


    @Override
    public AuthResponse login(LoginRequest request) {
        // Let Spring Security handle authentication (validates credentials)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        // Fetch the user from DB after authentication
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + request.getEmail()));

        String token = jwtService.generateToken(new UserDetailsImpl(user));

        return AuthResponse.builder()
                .token(token)
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();


    }
}
