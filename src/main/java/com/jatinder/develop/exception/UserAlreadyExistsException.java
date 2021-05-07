package com.pinakey.authentication.exception;

import lombok.Data;

/**
 * @author Jatinder
 * @since 11/15/2020 9:27 PM
 */
@Data
public class UserAlreadyExistsException extends RuntimeException {

    private String message;

    public UserAlreadyExistsException(String message) {
        this.message = message;
    }
}
