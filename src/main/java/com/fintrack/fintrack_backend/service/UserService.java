package com.fintrack.fintrack_backend.service;

import com.fintrack.fintrack_backend.model.User;
import com.fintrack.fintrack_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.fintrack.fintrack_backend.dto.RegisterRequest;
import com.fintrack.fintrack_backend.dto.UserProfileResponse;

import java.util.stream.Collectors;     
import java.util.List;
@Service

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserProfileResponse createUser(RegisterRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encodedPassword);
        User saved = userRepository.save(user);
        return new UserProfileResponse(saved.getId(), saved.getName(), saved.getEmail());
    }

    public List<UserProfileResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserProfileResponse(u.getId(), u.getName(), u.getEmail()))
                .collect(Collectors.toList());
    }

}
