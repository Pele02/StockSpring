package com.stockspring.service;

import com.stockspring.dto.LoginDTO;
import com.stockspring.dto.RegisterDTO;

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
     * @param registerDTO the {@link RegisterDTO} containing the user's details
     */
    void addUser(RegisterDTO registerDTO);

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
    boolean isAuthenticated(LoginDTO loginDTO);

    /**
     * Sends a password reset email to the user if the provided email exists in the system.
     *<p>
     * This method generates a unique password reset token, associates it with the user,
     * and sets an expiration time for the token. The token is then saved to the database
     * and a reset email is sent to the user containing a link to reset their password.
     *</p>
     *
     * @param email the email address of the user requesting a password reset
     * @return {@code true} if the email exists and the reset email is sent successfully;
     *         {@code false} if the email is not found or the process fails
     */
    boolean sendPasswordResetEmail(String email);

    /**
     * Sends a password reset email to the specified user.
     *<p>
     * This method constructs the email content, including the subject and body,
     * and sends it using the configured JavaMailSender. The reset link provided
     * should allow the user to reset their password.
     *</p>
     *
     * @param email the recipient's email address
     * @param resetLink the unique link to reset the user's password
     */
    void sendResetEmail(String email, String resetLink);

    /**
     * Resets the user's password using a valid password reset token.
     *<p>
     * This method verifies the provided reset token and updates the user's password
     * if the token is valid and has not expired. After successfully resetting the
     * password, the token is removed from the database to prevent reuse.
     *</p>
     *
     * @param token the unique password reset token associated with the user
     * @param newPassword the new password to be set for the user
     * @return {@code true} if the password was successfully reset; {@code false} otherwise
     */
    boolean resetPassword(String token, String newPassword);

    /**
     * Deletes the user from the database.
     *
     * @param username the username of the user to delete
     * @return {@code true} if the user was successfully deleted; {@code false} otherwise
     */
    boolean deleteUser(String username);
}
