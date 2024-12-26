package com.stockspring.service;

import com.stockspring.dto.UserDTO;

public interface UserService {
    // Register new user
    void addUser(UserDTO userDTO);

    // Check if the email exist
    boolean existsByUsername(String username);
}
