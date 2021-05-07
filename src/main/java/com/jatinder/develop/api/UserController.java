package com.pinakey.authentication.api;

import com.pinakey.authentication.model.User;
import com.pinakey.authentication.repository.UserRepository;
import com.pinakey.authentication.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/***
 * @author Jatinder
 * @since 11/15/2020 8:32 PM
 * @version 0.0.1
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userDetailService.saveUser(user);
    }

    @DeleteMapping("/{user-id}")
    public String deleteUser(@PathVariable("user-id") Long userId) {
        boolean user = userRepository.existsById(userId);
        if (user) {
            userRepository.deleteById(userId);
            return "User deleted successfully!";
        } else {
            throw new UsernameNotFoundException("User you are trying to delete does not exist!");
        }
    }

    @PatchMapping
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/{username}")
    public User fetchUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
    }

}
