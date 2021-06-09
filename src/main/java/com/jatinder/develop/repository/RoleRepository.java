package com.jatinder.develop.repository;

import com.jatinder.develop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 * @author Jatinder
 * @since 1.0.0
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByAuthority(String authority);
}
