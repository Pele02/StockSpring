package com.stockspring.service;

import com.stockspring.dto.UserDTO;

/**
 * Service interface for managing user-related operations.
 *
 * <p>
 * Implementing classes should provide the logic for interacting with the
 * persistence layer and handling user-related business operations.
 * </p>
 *
 * @version 1.0
 */
public interface UserService {

    /**
     * Register new user.
     *
     * @param userDTO the {@link UserDTO} containing the user's details
     */
    void addUser(UserDTO userDTO);

    /**
     * Check if the username is in the database.
     *
     * @param username the username to check
     * @return {@code true} if the username exists, {@code false} otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if the email is in the database.
     *
     * @param email the username to check
     * @return {@code true} if the username exists, {@code false} otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Verifies if the provided username and password match a registered user.
     *
     * @param username the username to authenticate
     * @param password the password associated with the username
     * @return {@code true} if the credentials are valid, {@code false} otherwise
     */
    boolean isAuthenticated(String username, String password);
}
