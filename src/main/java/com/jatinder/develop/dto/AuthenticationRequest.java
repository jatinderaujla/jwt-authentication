package com.jatinder.develop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
/***
 * @author Jatinder
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {
    private String username;
    private String password;
}
