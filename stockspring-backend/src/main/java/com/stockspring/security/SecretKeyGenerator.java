package com.stockspring.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Base64;

/**
 * Class to generate a secret key for JWT signing.
 * <p>
 * This class is responsible for generating a new secret key for the HS256 signature algorithm,
 * encoding it in Base64 format, and printing the encoded key. The generated key is used in the
 * JWT utility class to sign and verify JWT tokens securely.
 * </p>
 */
public class SecretKeyGenerator {
    /**
     * Main method that generates a secret key for JWT token signing.
     * <p>
     *  It generates a new secret key using the HS256 signature algorithm, converts the key to
     *  Base64 encoding, and prints the encoded key to the console.
     * </p>
     *
     * <p>
     *  Key should be added to <code> application.properties</code>
     * </p>
     *
     * @param args Command-line arguments (not used in this implementation).
     */
    public static void main(String[] args) {

        // Generate the secret key for HS256
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

        // Convert the key to Base64
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());

        // Print the Base64 encoded key
        System.out.println("New generated key (Base64): " + encodedKey);
    }
}
