package com.stockspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for handling user login information.
 * <p>
 *  This class is used to encapsulate the username and password for
 *  authentication purposes in the application.
 * </p>
 *
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String username;

    private String password;

}
