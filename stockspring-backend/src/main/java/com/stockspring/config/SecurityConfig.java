package com.stockspring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for development (enable in production with proper setup)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register").permitAll() // Allow public access to /register
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .httpBasic(AbstractHttpConfigurer::disable) // Disable HTTP Basic Authentication if not needed
                .formLogin(AbstractHttpConfigurer::disable); // Disable the default form login

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}
