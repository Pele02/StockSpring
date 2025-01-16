package com.stockspring.entity;

import jakarta.persistence.*;

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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;


    @Column(name = "email")
    private String email;

    @Column(name = "password_hash")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PasswordResetToken> passwordResetTokens;


    // Default constructor for JPA
    public User() {}

    // Manually added constructor with parameters
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
