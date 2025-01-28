package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Access(AccessType.FIELD)
    @Column(name = "password_hash")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PasswordResetToken> passwordResetTokens;

}
