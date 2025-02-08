package com.stockspring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stockspring.dto.LoginDTO;
import com.stockspring.dto.RegisterDTO;
import com.stockspring.service.UserService;
import com.stockspring.utility.JwtUtility;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for managing user-related operations.
 * <p>
 *  This class handles user registration and login requests, providing necessary
 *  endpoints for the StockSpring application.
 * </p>
 *
 * <p>
 *  Cross-origin requests are allowed for the frontend hosted on
 *  <code><a href="http://localhost:5173"></a></code>.
 * </p>
 *
 * @version 1.0
 */
@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Registers a new user in the application.
     *
     * <p>
     *     Check if there is already registered the email and user, if they are not registered, create new user.
     * </p>
     *
     * @param registerDTO the data transfer object containing user registration details
     * @return a {@link ResponseEntity} with an appropriate HTTP status and message
     */
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO) {
        if (userService.existsByUsername(registerDTO.getUsername()) || userService.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The username or email is already in use!");
        }

        try {
            // Add the user to the database
            userService.addUser(registerDTO);

            // Return the token in the response body
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully! Please log in.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the user: " + e.getMessage());
        }
    }

    /**
     * Log in user and generates a JWT token if successful.
     *
     * <p>
     *     This method checks the username and password against stored credentials. If
     *      authentication succeeds, a JWT token is generated and returned.
     * </p>
     *
     * @param loginDTO the data transfer object containing user registration details
     * @return a {@link ResponseEntity} with an appropriate HTTP status and message
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        // Authenticate user (use your authentication service)
        boolean isAuthenticated = userService.isAuthenticated(loginDTO);
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Generate JWT
        String token = jwtUtility.generateToken(loginDTO.getUsername());

        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"token\": \"" + token + "\"}");
    }

    /**
     * Endpoint to request a password reset email.
     *<p>
     *     This methode sends an email to the User that requested the password reset if the User
     *     is in the database.
     *</p>
     *
     * @param email the email address of the user requesting a password reset
     * @return a {@link ResponseEntity} with a success message if the email was sent,
     *         or a 404 status if the email is not associated with any user
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> resetPasswordRequest(@RequestParam String email) {
        boolean isEmailSent = userService.sendPasswordResetEmail(email);

        if (isEmailSent) {
            return ResponseEntity.ok("Password reset email sent successfully.");
        } else {
            return ResponseEntity.status(404).body("Email not found.");
        }
    }

    /**
     * Endpoint to reset the user's password using a reset token.
     *<p>
     *     The User has a given time to reset his password.
     *</p>
     *
     * @param token the reset token provided to the user
     * @param newPassword the new password to set for the user
     * @return a {@link ResponseEntity} with a success message if the password was reset,
     *         or a 400 status if the token is invalid or expired
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        boolean isPasswordReset = userService.resetPassword(token, newPassword);

        if (isPasswordReset) {
            return ResponseEntity.ok("Password reset successfully.");
        } else {
            return ResponseEntity.status(400).body("Invalid or expired token.");
        }
    }

    @DeleteMapping("/delete-account")
   public ResponseEntity<String> deleteAccount(@RequestBody String jsonToken) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(jsonToken);
        String token = jsonObject.getString("token");

    try {
        // Extract username from token
        String username = jwtUtility.extractUsername(token);

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        // Delete user account
        if (userService.deleteUser(username)) {
            return ResponseEntity.ok("Account deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete account");
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
    }
}

}
