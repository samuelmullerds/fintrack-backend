package com.fintrack.fintrack_backend.controller;
import com.fintrack.fintrack_backend.dto.LoginRequest;
import com.fintrack.fintrack_backend.dto.LoginResponse;
import com.fintrack.fintrack_backend.dto.RegisterRequest;
import com.fintrack.fintrack_backend.dto.UserProfileResponse;
import com.fintrack.fintrack_backend.service.AuthService;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints para autenticação de usuários")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário e retorna um token de acesso")
    public LoginResponse login(@Parameter(description = "Dados de login do usuário") @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cria um novo usuário com as informações fornecidas")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/me")
    @Operation(summary = "Obter informações do usuário logado", description = "Retorna as informações do usuário autenticado")
    public UserProfileResponse getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return authService.getCurrentUser(email);
    }
    }

