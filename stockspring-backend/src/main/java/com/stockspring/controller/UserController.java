package com.stockspring.controller;

import com.stockspring.dto.LoginDTO;
import com.stockspring.dto.UserDTO;
import com.stockspring.service.AuthenticationService;
import com.stockspring.service.UserService;
import com.stockspring.utility.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        if (userService.existsByUsername(userDTO.getEmail())) {
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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        // Authenticate user (use your authentication service)
        boolean isAuthenticated = authenticationService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Generate JWT
        String token = jwtUtility.generateToken(loginDTO.getUsername());

        // Return token in the response
//        return ResponseEntity.ok(new HashMap<String, String>() {{
//            put("token", token);
//        }});
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(loginDTO.getUsername() + " logged in successfully!");
    }
}
