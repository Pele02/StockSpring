package com.stockspring.repository;

import com.stockspring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * Provides CRUD operations and custom query methods for the {@code User} entity
 * by extending the {@link JpaRepository} interface.
 * </p>
 *
 * <p>
 * This repository is marked with {@link EnableJpaRepositories} to enable JPA repository support
 * and {@link Repository} to indicate its role in the persistence layer.
 * </p>
 *
 * @version 1.0
 */
@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retrieves a {@link User} entity based on its username.
     *
     * @param username the username to search for
     * @return an {@link Optional} containing the found {@code User}, or empty if no user is found
     */
    Optional<User> findByUsername(String username);

    /**
     * Retrieves a {@link User} entity based on its username.
     *
     * @param email the username to search for
     * @return an {@link Optional} containing the found {@code User}, or empty if no user is found
     */
    Optional<User> findByEmail(String email);
}
