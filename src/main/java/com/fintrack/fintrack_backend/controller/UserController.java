package com.fintrack.fintrack_backend.controller;

import com.fintrack.fintrack_backend.dto.UserProfileResponse;
import com.fintrack.fintrack_backend.service.UserService;
import org.springframework.web.bind.annotation.*;
import com.fintrack.fintrack_backend.dto.RegisterRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;
@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Gerenciamento dos usuários do sistema")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Criar novo usuário", description = "Cria um novo usuário no sistema")
    @PostMapping
    public UserProfileResponse createUser(@Parameter(description = "Dados do usuário a ser criado") @RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados no sistema")
    @GetMapping
    public List<UserProfileResponse> getAllUsers() {
        return userService.getAllUsers();
    }
}
