package com.stockspring.service;

import com.stockspring.model.User;

import java.util.List;

public interface UserService {
    // Retrieve all users
    List <User> getAllUsers();

    // Retrieve a user by ID
    User getUserById(Long id);

    // Add new user
    User addUser(User user);

    // Update an existing user
    User updateUser(User user);

    // Delete an existing user
    String deleteUser(User user);
}
