package com.fintrack.fintrack_backend.controller;
import com.fintrack.fintrack_backend.dto.LoginRequest;
import com.fintrack.fintrack_backend.model.User;
import com.fintrack.fintrack_backend.repository.UserRepository;
import com.fintrack.fintrack_backend.security.JwtService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.fintrack.fintrack_backend.dto.LoginResponse;
import com.fintrack.fintrack_backend.dto.RegisterRequest;
import com.fintrack.fintrack_backend.dto.UserProfileResponse;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autenticação de usuários")
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
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token de acesso")
    public LoginResponse login(@Parameter(description = "Dados de login do usuário") @RequestBody LoginRequest request) {
        
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));
        
        boolean passwordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatch) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos");
        }

        String token = jwtService.generateToken(user.getId());

        return new LoginResponse(
            user.getId(),
            user.getName(),
            user.getEmail(),
            token
        );
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário com as informações fornecidas")
    public void register(@RequestBody RegisterRequest request) {

        boolean exists = userRepository.findByEmail(request.getEmail()).isPresent();

        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já está em uso");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @GetMapping("/me")
    @Operation(summary = "Obter informações do usuário logado", description = "Retorna as informações do usuário autenticado")
    public UserProfileResponse getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha inválidos"));

        return new UserProfileResponse(
            user.getId(),
            user.getName(),
            user.getEmail()
        );
    }
    }

