package com.FleetGuard360.backend.controller;

import com.FleetGuard360.backend.model.User;
import com.FleetGuard360.backend.repository.UserRepository;
import com.FleetGuard360.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }
}
