package com.stockspring.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for {@link JwtUtility} to ensure proper functionality of JWT token generation,
 * username extraction, validation, and expiration handling.
 *
 * <p>This test class contains the following tests:</p>
 * <ul>
 *   <li>{@link #testGenerateToken()} - Validates that a JWT token is generated successfully.</li>
 *   <li>{@link #testExtractUsername()} - Ensures the correct username is extracted from a JWT token.</li>
 *   <li>{@link #testTokenExpiration()} - Verifies that tokens expire correctly after a set duration.</li>
 *   <li>{@link #testValidateToken()} - Confirms that a token is valid when checked with the correct username.</li>
 *   <li>{@link #testValidateTokenInvalidUsername()} - Ensures that a token is invalid when the username does not match.</li>
 * </ul>
 *
 * @version 1.0
 */
@SpringBootTest
class JwtUtilityTest {

    private JwtUtility jwtUtility;

    @Value("${JWT_SECRET}")
    private String secretKey;

    /**
     * Sets up the test environment before each test.
     * Initializes a new instance of {@link JwtUtility} and injects test-specific values
     * such as the secret key and expiration time.
     */
    @BeforeEach
    void setUp() {
        jwtUtility = new JwtUtility();
        // Inject test values for secret key and expiration time
        ReflectionTestUtils.setField(jwtUtility, "secretKey", secretKey);
        ReflectionTestUtils.setField(jwtUtility, "jwtExpirationTime", 1000 * 60 * 60); // 1 hour
    }

    /**
     * Tests the generation of a JWT token.
     *
     * <p>Validates that a token is generated successfully for a given username.</p>
     */
    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtUtility.generateToken(username);

        assertNotNull(token, "Token should not be null");
    }

    /**
     * Tests the extraction of the username from a JWT token.
     *
     * <p>Validates that the username extracted from the token matches the original username used during token creation.</p>
     */
    @Test
    void testExtractUsername() {
        String username = "testUser";
        String token = jwtUtility.generateToken(username);

        String extractedUsername = jwtUtility.extractUsername(token);
        assertEquals(username, extractedUsername, "The extracted username should match the original username");
    }

    /**
     * Tests the expiration behavior of a JWT token.
     *
     * <p>Generates a token with a short expiration time (1 second) and verifies that the token becomes invalid after expiration.</p>
     *
     * @throws InterruptedException if the thread is interrupted during sleep
     */
    @Test
    void testTokenExpiration() throws InterruptedException {
        ReflectionTestUtils.setField(jwtUtility, "jwtExpirationTime", 1000); // 1 second for testing
        String token = jwtUtility.generateToken("testUser");

        // Wait for the token to expire
        Thread.sleep(1500);

        boolean isExpired = jwtUtility.validateToken(token, "testUser");
        assertFalse(isExpired, "Expired tokens should be invalid");
    }

    /**
     * Tests the validation of a JWT token for a valid username.
     *
     * <p>Validates that a token is considered valid when it matches the correct username.</p>
     */
    @Test
    void testValidateToken() {
        String username = "testUser";
        String token = jwtUtility.generateToken(username);

        boolean isValid = jwtUtility.validateToken(token, username);
        assertTrue(isValid, "The token should be valid for the correct username");
    }

    /**
     * Tests the validation of a JWT token for an invalid username.
     *
     * <p>Validates that a token is considered invalid when the username in the token does not match the provided username.</p>
     */
    @Test
    void testValidateTokenInvalidUsername() {
        String token = jwtUtility.generateToken("testUser");

        boolean isValid = jwtUtility.validateToken(token, "wrongUser");
        assertFalse(isValid, "The token should be invalid for a mismatched username");
    }
}
