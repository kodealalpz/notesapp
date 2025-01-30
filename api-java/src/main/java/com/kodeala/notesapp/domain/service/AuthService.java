package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.request.LoginRequest;
import com.kodeala.notesapp.domain.dto.request.RegisterRequest;
import com.kodeala.notesapp.domain.dto.response.auth.AuthResponse;
import com.kodeala.notesapp.domain.dto.response.UserResponse;
import com.kodeala.notesapp.domain.repository.UserRepository;
import com.kodeala.notesapp.domain.security.jwt.JwtService;
import com.kodeala.notesapp.persistence.entity.User;
import com.kodeala.notesapp.persistence.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<UserResponse> register(RegisterRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User newUser = UserMapper.toUser(request);
        return Optional.of(UserMapper.toUserDTO(userRepository.create(newUser)));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails user = userRepository.getByUsername(request.getUsername())
                .map(u -> u)
                .orElseThrow();

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
