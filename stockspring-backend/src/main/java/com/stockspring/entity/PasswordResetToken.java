package com.stockspring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

 /**
 * Entity representing a password reset token.
 *
 * <p>Used to manage password reset requests by associating a unique token
 * with a specific user and an expiration date.</p>
 *
 *  @version 1.0
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_seq")
    @SequenceGenerator(name = "password_reset_token_seq", sequenceName = "password_reset_token_seq", allocationSize = 1)
    private Long id;

    @Column
    private String token;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Date expiryDate;

     public PasswordResetToken(String token, User user, Date expiryDate) {
         this.token = token;
         this.user = user;
         this.expiryDate = expiryDate;
     }
}
