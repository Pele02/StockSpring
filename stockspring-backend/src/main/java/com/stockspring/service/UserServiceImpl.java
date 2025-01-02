package com.stockspring.service;

import com.stockspring.dto.UserDTO;
import com.stockspring.model.User;
import com.stockspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} interface for managing user operations.
 *
 * <p>
 * The implementation interacts with the {@link UserRepository} to perform database operations
 * and uses {@link PasswordEncoder} to securely handle user passwords.
 * </p>
 *
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user by saving their details to the database.
     * <p>
     * The password is securely hashed using {@link PasswordEncoder} before saving.
     * </p>
     *
     * @param userDTO the {@link UserDTO} containing the user's details
     */
    @Override
    public void addUser(UserDTO userDTO) {

        User user = new User(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword())

        );

        userRepository.save(user);
    }

    /**
     * Checks if a user with the given username exists in the database.
     *
     * @param username the username to check
     * @return {@code true} if the username exists, {@code false} otherwise
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Checks if a user with the given username exists in the database.
     *
     * @param email the username to check
     * @return {@code true} if the username exists, {@code false} otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Authenticates a user by verifying the provided password matches the stored password.
     * <p>
     * This method fetches the user by username and uses {@link PasswordEncoder} to compare
     * the provided password with the encrypted password in the database.
     * </p>
     *
     * @param username the username to authenticate
     * @param password the raw password to verify
     * @return {@code true} if the username exists and the password is correct, {@code false} otherwise
     */
    @Override
    public boolean isAuthenticated(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }
}
