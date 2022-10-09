package com.techdemy.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testUser")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String testUser(){
        return "User can access";
    }

    @GetMapping("/testAdmin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String testAdmin(){
        return "Admin can access";
    }

}
