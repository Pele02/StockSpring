package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * Represents a User entity in the StockSpring application.
 * This class is mapped to the "users" table in the "stockspring_schema" database schema.
 *
 *  <p>
 *  Each User has an auto-generated ID, a username, an email, and a hashed password.
 *  </p>
 *
 *  @version 1.0
 */
@Entity
@Table(name = "users", schema = "stockspring_schema")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Access(AccessType.FIELD)
    @Column(name = "password_hash", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PasswordResetToken> passwordResetTokens;

    @OneToMany(mappedBy = "userPortfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Portfolio> portfolios;

}
