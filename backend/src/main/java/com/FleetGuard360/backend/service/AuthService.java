package com.FleetGuard360.backend.service;

import com.FleetGuard360.backend.dto.AuthRequest;
import com.FleetGuard360.backend.dto.AuthResponse;
import com.FleetGuard360.backend.dto.RegisterRequest;
import com.FleetGuard360.backend.model.Role;
import com.FleetGuard360.backend.model.User;
import com.FleetGuard360.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena()));
        } catch (Exception e) {
            throw new AuthException("Correo o contraseña incorrectos");
        }

        User user = userRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new AuthException("Correo no encontrado"));
        String token=jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .usuario(user)
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        if(userRepository.findByCorreo(request.getCorreo()).isPresent()) {
            throw new UserAlreadyExistsException("Correo ya existe");
        }
        User user = User.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .contrasena(bCryptPasswordEncoder.encode( request.getContrasena()))
                .rol(request.getRol())
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    public static class AuthException extends RuntimeException {
        public AuthException(String message) {
            super(message);
        }
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}


