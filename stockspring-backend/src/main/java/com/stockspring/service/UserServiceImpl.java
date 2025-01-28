// UserServiceImpl.java
package com.stockspring.service;

import com.stockspring.dto.RegisterDTO;
import com.stockspring.entity.PasswordResetToken;
import com.stockspring.entity.User;
import com.stockspring.repository.PasswordResetTokenRepository;
import com.stockspring.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addUser(RegisterDTO registerDTO) {
        User user = modelMapper.map(registerDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public boolean isAuthenticated(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public boolean sendPasswordResetEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        Date expiryDate = new Date(System.currentTimeMillis() + 900000);

        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, expiryDate);
        passwordResetTokenRepository.save(passwordResetToken);

        String resetLink = "http://localhost:8081/auth/reset-password?token=" + token;
        sendResetEmail(email, resetLink);

        return true;
    }

    @Override
    public void sendResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Resetează parola");
        message.setText("Pentru a reseta parola apăsați link-ul:\n" + resetLink);

        javaMailSender.send(message);
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> passwordResetTokenOptional = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenOptional.isEmpty()) {
            return false;
        }

        PasswordResetToken passwordResetToken = passwordResetTokenOptional.get();

        if (passwordResetToken.getExpiryDate().before(new Date())) {
            return false;
        }

        User user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);

        return true;
    }

    @Override
    public boolean deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }
}