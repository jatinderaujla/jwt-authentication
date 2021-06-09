package com.jatinder.develop.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/secured/admin")
    @Secured({"ROLE_ADMIN"})
    public String testAdminRole(){
        return "admin role";
    }

    @GetMapping("/secured/user")
    @Secured({"ROLE_USER"})
    public String testUserRole(){
        return "user role";
    }

    @GetMapping("/secured/two")
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    public String testTwoRoles(){
        return "admin, user role";
    }

    /**
     *
     * Pre and Post authentication
     */

    @GetMapping("/pre/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testAdminRole2(){
        return "admin role";
    }

    @GetMapping("/pre/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUserRole2(){
        return "user role";
    }

    @GetMapping("/pre/two")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public String testTwoRoles2(){
        return "admin, user role";
    }

    @GetMapping("/pre/or/two")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    public String testOrTwoRoles2(){
        return "admin or user role";
    }
}
