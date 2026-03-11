package com.fintrack.fintrack_backend.controller;
import com.fintrack.fintrack_backend.dto.LoginRequest;
import com.fintrack.fintrack_backend.model.User;
import com.fintrack.fintrack_backend.repository.UserRepository;
import com.fintrack.fintrack_backend.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fintrack.fintrack_backend.dto.LoginResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            token
        );
    }
}
