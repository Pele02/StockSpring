package com.stockspring.service;

import com.stockspring.dto.UserDTO;
import com.stockspring.entity.PasswordResetToken;
import com.stockspring.entity.User;
import com.stockspring.repository.PasswordResetTokenRepository;
import com.stockspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

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

    /**
     * Sends an email to the user to reset the password
     *
     * @param email the email of the user
     * @return {@code true} if the email exists and the reset request is send, {@code false} if the email is not found or the process fails
     */
    @Override
    public boolean sendPasswordResetEmail(String email) {
        //Check if email exists
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()){
            //The email doesn't exist
            return false;
        }

        User user = userOptional.get();

        //Generate reset token
        String token = UUID.randomUUID().toString();

        //Set token expiration time
        Date expiryDate = new Date(System.currentTimeMillis() + 900000);

        // Save the token in the database
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);

        // Send the reset email
        String resetLink = "http://localhost:8081/reset-password?token=" + token;
        sendResetEmail(email, resetLink);

        return true; // Return true if the email was sent successfully
    }

    /**
     * Implements the body format of the reset email
     *
     * @param email     email of the user
     * @param resetLink
     */
    @Override
    public void sendResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Resetează parola");
        message.setText("Pentru a reseta parola apăsați link-ul:\n" + resetLink);

        javaMailSender.send(message);
    }

    /**
     * @param token
     * @param newPassword
     * @return
     */
    @Override
    public boolean resetPassword(String token, String newPassword) {
        // Find the token in the database
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenOptional.isEmpty()) {
            return false; // Token not found
        }

        PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();

        // Check if the token has expired
        if (passwordResetToken.getExpiryDate().before(new Date())) {
            return false; // Token expired
        }

        // Get the user associated with the token
        User user = passwordResetToken.getUser();

        // Update the user's password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Delete the used token
        passwordResetTokenRepository.delete(passwordResetToken);

        return true; // Password reset successful
    }
}
