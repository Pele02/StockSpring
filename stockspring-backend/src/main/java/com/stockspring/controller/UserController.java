package com.stockspring.controller;

import com.stockspring.dto.LoginDTO;
import com.stockspring.dto.UserDTO;
import com.stockspring.service.UserService;
import com.stockspring.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing user-related operations.
 * <p>
 *  This class handles user registration and login requests, providing necessary
 *  endpoints for the StockSpring application.
 * </p>
 *
 * <p>
 *  Cross-origin requests are allowed for the frontend hosted on
 *  <code>http://localhost:5173</code>.
 * </p>
 *
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;


    /**
     * Registers a new user in the application.
     *
     * <p>
     *     Check if there is a email, if not register new user
     * </p>
     *
     * @param userDTO the data transfer object containing user registration details
     * @return a {@link ResponseEntity} with an appropriate HTTP status and message
     */
    @Transactional
    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        if (userService.existsByUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username is already in use!");
        }

        if (userService.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is already in use!");
        }

        try {
            userService.addUser(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while registering the user: " + e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully!");
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
        boolean isAuthenticated = userService.isAuthenticated(loginDTO.getUsername(), loginDTO.getPassword());
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Generate JWT
        String token = jwtUtility.generateToken(loginDTO.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loginDTO.getUsername() + " logged in successfully!");
    }
}
