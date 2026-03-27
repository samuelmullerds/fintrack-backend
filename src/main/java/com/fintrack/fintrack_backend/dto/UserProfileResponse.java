package com.fintrack.fintrack_backend.dto;

    public record UserProfileResponse(Long id, String name, String email) {
    public UserProfileResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    }

