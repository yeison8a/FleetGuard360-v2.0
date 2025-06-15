package com.FleetGuard360.backend.service;

import com.FleetGuard360.backend.model.User;
import com.FleetGuard360.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        user.setContrasena(passwordEncoder.encode(user.getContrasena()));
        userRepository.save(user);
    }
}
