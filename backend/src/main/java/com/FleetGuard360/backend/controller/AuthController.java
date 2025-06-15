package com.FleetGuard360.backend.controller;

import com.FleetGuard360.backend.dto.AuthRequest;
import com.FleetGuard360.backend.dto.AuthResponse;
import com.FleetGuard360.backend.dto.RegisterRequest;
import com.FleetGuard360.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

     @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
         return ResponseEntity.ok(authService.login(request));
     }

     @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
         return ResponseEntity.ok(authService.register(request));
     }
}
