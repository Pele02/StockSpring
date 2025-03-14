package com.stockspring.controller;

import com.stockspring.entity.PasswordResetToken;
import com.stockspring.entity.Role;
import com.stockspring.entity.User;
import com.stockspring.entity.UserRole;
import com.stockspring.exception.APIException;
import com.stockspring.repository.PasswordResetTokenRepository;
import com.stockspring.repository.RoleRepository;
import com.stockspring.repository.UserRepository;
import com.stockspring.security.jwt.JwtUtils;
import com.stockspring.security.request.LoginRequest;
import com.stockspring.security.request.SignupRequest;
import com.stockspring.security.response.MessageResponse;
import com.stockspring.security.response.UserInfoResponse;
import com.stockspring.security.service.UserDetailsImpl;
import com.stockspring.service.EmailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailServiceImpl emailService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName(UserRole.ROLE_USER)
                    .orElseThrow(() -> new APIException("Role is not found.", HttpStatus.NOT_FOUND.value()));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRoleName(UserRole.ROLE_ADMIN)
                                .orElseThrow(() -> new APIException("Role is not found.", HttpStatus.NOT_FOUND.value()));
                        roles.add(adminRole);
                        break;
                    case "user":
                        Role userRole = roleRepository.findByRoleName(UserRole.ROLE_USER)
                                .orElseThrow(() -> new APIException("Role is not found.", HttpStatus.NOT_FOUND.value()));
                        roles.add(userRole);
                        break;
                    default:
                        throw new APIException("Invalid role.", HttpStatus.BAD_REQUEST.value());
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    /**
     * Endpoint to authenticate a user and generate a JWT token.
     * <p>
     * This method authenticates the user using the provided username and password,
     * and generates a JWT token for the user if the credentials are valid.
     * </p>
     *
     * @param loginRequest the login request containing the user's credentials
     * @return a {@link ResponseEntity} with the user's information and JWT token if the
     * credentials are valid, or a 404 status if the credentials are invalid
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(), roles);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(response);
    }

    @GetMapping("/username")
    public String getUsername(Authentication authentication) {
        if (authentication != null) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getUsername();
        }
        return null;
    }

    @GetMapping("/user-details")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse response = new UserInfoResponse(userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.deleteJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                cookie.toString()).body("You have been logged out successfully.");
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("email") String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600000)); // 1 hour expiry
        tokenRepository.save(resetToken);


        String resetLink = "http://localhost:8081/auth/reset-password?token=" + token;
        emailService.sendResetEmail(email, resetLink);

        return "Password reset link sent to your email.";
    }

    @PutMapping("/reset-password/confirm")
    public String confirmResetPassword(@RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        // Check if the token is expired
        if (resetToken.getExpiryDate().before(new Date())) {
            throw new RuntimeException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);

        // Optionally, delete the token after successful password reset
        List<PasswordResetToken> userTokens = tokenRepository.findByUser(user);
        tokenRepository.deleteAll(userTokens);

        return "Password reset successfully.";
    }

    @DeleteMapping("/delete-account")
    @Transactional
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new APIException("User not found", HttpStatus.NOT_FOUND.value()));

        // Clear associations
        user.getPasswordResetTokens().clear();
        user.getPortfolios().clear();
        user.getRoles().clear();
        userRepository.save(user);

        // Now delete the user
        userRepository.delete(user);

        return ResponseEntity.ok().body("Account deleted successfully");
    }
}

