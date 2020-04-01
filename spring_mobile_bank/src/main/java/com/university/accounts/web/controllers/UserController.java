package com.university.accounts.web.controllers;

import com.university.accounts.backend.dto.CredentialsDto;
import com.university.accounts.backend.dto.UserDto;
import com.university.accounts.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity createUser(@RequestBody UserDto user) {
        userService.createUser(user);
        return ResponseEntity.ok("User is created successfully");
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> loginUser(@RequestBody CredentialsDto credentials) {
        String token = userService.loginUser(credentials);
        return ResponseEntity.ok("Bearer " + token);
    }
}
