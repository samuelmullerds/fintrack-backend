package com.fintrack.fintrack_backend.dto;

public record LoginResponse(Long userId, String name, String email, String token) {
    public LoginResponse(Long userId, String name, String email, String token) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.token = token;
    }
}
