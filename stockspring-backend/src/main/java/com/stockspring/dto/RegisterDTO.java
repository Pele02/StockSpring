package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for user registration
 * <p>
 *  This class is used to encapsulate the username, email and password for
 *  registration purposes in the application.
 * </p>
 *
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

}
