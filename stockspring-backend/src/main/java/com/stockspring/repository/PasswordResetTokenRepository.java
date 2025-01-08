package com.stockspring.repository;

import com.stockspring.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing password reset tokens in the database.
 *
 * <p>Provides methods for interacting with the {@link PasswordResetToken} entity,
 * including retrieving tokens by their value.</p>
 */
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Finds a password reset token by its value.
     *
     * @param token the value of the reset token
     * @return an {@link Optional} containing the found token, or empty if not found
     */
    Optional<PasswordResetToken> findByToken(String token);
}
