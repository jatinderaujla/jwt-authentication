package com.jatinder.develop.service;

import com.jatinder.develop.exception.UserAlreadyExistsException;
import com.jatinder.develop.model.Role;
import com.jatinder.develop.model.User;
import com.jatinder.develop.repository.RoleRepository;
import com.jatinder.develop.repository.UserRepository;
import com.jatinder.develop.exception.UserAlreadyExistsException;
import com.jatinder.develop.repository.RoleRepository;
import com.jatinder.develop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/***
 * @author Jatinder
 * @since 1.0.0
 */
@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password !"));
    }

    /***
     * @apiNote Save user details with encrypted password and assign the role to user
     * @param user
     * @return User with assinged role
     */
    public User saveUser(User user) {
        final Optional<User> isUser = userRepository.findByUsername(user.getUsername());
        if (isUser.isPresent()) {
            throw new UserAlreadyExistsException(String.format("Username %s already exists!", user.getUsername()));
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Optional<Role> role = roleRepository.findByAuthority("ROLE_USER");
        role.ifPresent(value -> user.setAuthorities(Stream.of(value).collect(Collectors.toSet())));
        return userRepository.save(user);
    }

    /***
     * @apiNote Update user details
     * @param user
     * @return Updated user details
     */
    public User updateUser(User user) {
        return null;
    }

    /***
     * @apiNote Update user password with encrypted
     * @param user
     * @param newPassword
     * @return
     */
    public User changePassword(User user, String newPassword) {
        return null;
    }
}