package com.jatinder.develop.api;

import com.jatinder.develop.model.Role;
import com.jatinder.develop.repository.RoleRepository;
import com.jatinder.develop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @author Jatinder
 * @since 1.0.0
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping
    public Role saveRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @PatchMapping
    public Role updateRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @DeleteMapping("/{role-id}")
    public String deleteRole(@PathVariable(name = "role-id") int roleId) {
        boolean role = roleRepository.existsById(roleId);
        if (role) {
            roleRepository.deleteById(roleId);
            return "Role deleted successfully !";
        } else {
            throw new RuntimeException("Role does not exists");
        }
    }

    @GetMapping
    public List<Role> fetchAllRoles() {
        return roleRepository.findAll();
    }

}
