package com.jatinder.develop.repository;

import com.jatinder.develop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 * @author Jatinder
 * @since 1.0.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
