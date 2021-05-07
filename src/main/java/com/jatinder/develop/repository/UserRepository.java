package com.pinakey.authentication.repository;

import com.pinakey.authentication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 * @author Jatinder
 * @since 11/15/2020 8:32 PM
 * @version 0.0.1
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}