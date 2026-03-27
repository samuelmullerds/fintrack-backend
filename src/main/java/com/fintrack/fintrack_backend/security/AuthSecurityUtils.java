package com.fintrack.fintrack_backend.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthSecurityUtils {

    public Long getAuthenticatedUserId() {
        Object principal = SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        if (principal instanceof Long) {
            return (Long) principal;
        }

        if (principal instanceof String) {
            return Long.parseLong((String) principal);
        }

        throw new RuntimeException("Usuário não autenticado");
    }
}