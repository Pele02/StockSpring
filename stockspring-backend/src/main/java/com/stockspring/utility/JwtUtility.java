package com.stockspring.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling JSON Web Tokens (JWT) operations.
 * <p>
 * This class provides methods for generating, parsing, and validating JWT tokens.
 * The tokens are signed with a secret key and have an expiration time.
 * </p>
 *
 * <p>
 * The secret key and expiration time are injected via application properties.
 * </p>
 *
 * @version 1.0
 */
@Component
public class JwtUtility {
    @Value("${JWT_SECRET}")
    private String secretKey;

    @Value("${JWT_EXPIRATION_TIME}")
    private long jwtExpirationTime;

    /**
     * Generates a JWT token for a given username.
     *
     * @param username the username for which the token is generated
     * @return a signed JWT token as a {@link String}
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    /**
     * Creates a JWT token with claims and a subject.
     *
     * @param claims additional claims to include in the token
     * @param subject the subject (typically the username)
     * @return a signed JWT token as a {@link String}
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Extracts the username (subject) from a JWT token.
     *
     * @param token the JWT token
     * @return the username as a {@link String}, or {@code null} if parsing fails
     */
    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token the JWT token
     * @return the expiration date as a {@link Date}, or {@code null} if parsing fails
     */
    public Date extractExpiration(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Validates a JWT token by verifying the username and checking its expiration.
     *
     * @param token the JWT token
     * @param username the expected username
     * @return {@code true} if the token is valid, {@code false} otherwise
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername != null && extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token the JWT token
     * @return {@code true} if the token is expired, {@code false} otherwise
     */
    private boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration == null || expiration.before(new Date());
    }
}
