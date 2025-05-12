package com.amit.devtracker.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {


    String generateToken(UserDetails userDetails);
    boolean isTokenValid (String token, UserDetails userDetails);
    String extractUsername(String token);
}
