package com.jatinder.develop.exception;

import lombok.Data;

/**
 * @author Jatinder
 * @since 1.0.0
 */
@Data
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
