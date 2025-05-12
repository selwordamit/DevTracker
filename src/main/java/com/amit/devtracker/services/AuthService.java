package com.amit.devtracker.services;

import com.amit.devtracker.domain.dtos.AuthResponse;
import com.amit.devtracker.domain.dtos.LoginRequest;
import com.amit.devtracker.domain.dtos.RegisterRequest;
import org.springframework.security.web.webauthn.api.AuthenticatorResponse;

import javax.security.auth.login.LoginContext;

public interface AuthService {

    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);

}
