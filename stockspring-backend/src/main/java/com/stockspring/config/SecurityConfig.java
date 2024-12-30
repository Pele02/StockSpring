package com.stockspring.config;

import com.stockspring.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security in the StockSpring application.
 * <p>
 *  This class defines the security filter chain and password encoder, and integrates the
 *  JWT authentication filter into the security configuration.
 * </p>
 *
 * @version 1.0
 */
@Configuration
public class SecurityConfig {

    /**
     * The JWT authentication filter used to process JWT tokens in incoming requests.
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructor injection for the JwtAuthenticationFilter.
     *
     * @param jwtAuthenticationFilter the JwtAuthenticationFilter to be used in the security configuration
     */
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configures the security filter chain for the application.
     * <p>
     * Disables CSRF protection (should be enabled in production with proper configuration),
     * disables default form login and HTTP Basic authentication, and adds the JWT authentication filter.
     * </p>
     *
     * @param http the {@link HttpSecurity} object used to configure security settings
     * @return the configured {@link SecurityFilterChain}
     * @throws Exception if an error occurs while building the security filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for development (enable in production with proper setup)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register", "/login").permitAll() // Allow public access to /register and /login
                        .anyRequest().authenticated() // Require authentication for all other endpoints
                )
                .httpBasic(AbstractHttpConfigurer::disable) // Disable HTTP Basic Authentication if not needed
                .formLogin(AbstractHttpConfigurer::disable) // Disable the default form login
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before the UsernamePasswordAuthenticationFilter

        return http.build();
    }


    /**
     * Encrypt the password of the user  before storing it in the database
     *
     * @return the new encrypted password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }
}
