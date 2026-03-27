package com.fintrack.fintrack_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;

import com.fintrack.fintrack_backend.model.User;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
public class FintrackBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(FintrackBackendApplication.class, args);
    }

}