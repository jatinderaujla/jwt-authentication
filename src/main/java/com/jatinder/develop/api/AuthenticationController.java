package com.jatinder.develop.api;

import com.jatinder.develop.dto.AuthenticationRequest;
import com.jatinder.develop.dto.AuthenticationResponse;
import com.jatinder.develop.model.User;
import com.jatinder.develop.service.AppUserDetailService;
import com.jatinder.develop.util.JWTUtil;
import com.jatinder.develop.dto.AuthenticationRequest;
import com.jatinder.develop.dto.AuthenticationResponse;
import com.jatinder.develop.service.AppUserDetailService;
import com.jatinder.develop.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * @author Jatinder
 * @since 1.0.0
 */

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AppUserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authManger;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/registration")
    public User createUser(@Validated @RequestBody User user) {
        return userDetailService.saveUser(user);
    }

    @PostMapping("/authentication")
    public AuthenticationResponse generateToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            Authentication authenticate = authManger.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password!");
        }

        return new AuthenticationResponse(jwtUtil.generateToken(authenticationRequest.getUsername()));
    }
}
