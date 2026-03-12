package com.fintrack.fintrack_backend.dto;

public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;

    public UserProfileResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
    
}
