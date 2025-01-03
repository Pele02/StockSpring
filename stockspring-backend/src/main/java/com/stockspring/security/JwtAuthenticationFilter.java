package com.stockspring.security;


import com.stockspring.utility.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT Authentication Filter for handling and validating JSON Web Tokens (JWT).
 * <p>
 * This filter intercepts HTTP requests to validate JWTs present in the
 * `Authorization` header and sets the authenticated user in the security context.
 * </p>
 *
 * <p>
 * It extends {@link OncePerRequestFilter}, ensuring that the filter is executed
 * once per request within a single request-processing cycle.
 * </p>
 *
 * <p>
 * The filter checks for a valid JWT, extracts the username, and sets the
 * {@link SecurityContextHolder} with an authenticated {@link UsernamePasswordAuthenticationToken}.
 * </p>
 *
 * @version 1.0
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtUtility;

    /**
     * Processes each incoming request to validate JWTs and set the authenticated user
     * in the security context if the token is valid.
     *
     * @param request     the {@link HttpServletRequest} object
     * @param response    the {@link HttpServletResponse} object
     * @param filterChain the {@link FilterChain} to pass the request/response to the next filter
     * @throws ServletException if an error occurs during request processing
     * @throws IOException      if an I/O error occurs during request processing
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Skip token validation for password reset
        if (request.getRequestURI().equals("/forgot-password")) {
            filterChain.doFilter(request, response); // Allow the request to proceed without validation
            return;
        }

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtUtility.extractUsername(token);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Validate the token
            if (jwtUtility.validateToken(token, username)) {
                // Create an Authentication object to set in the context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username, null, null  // You can provide authorities here if needed
                );
                // Set the authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}

