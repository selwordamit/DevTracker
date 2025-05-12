package com.amit.devtracker.services;

import com.amit.devtracker.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
    User getUserByEmail(String email);
    void deleteUserById(UUID id);
}
