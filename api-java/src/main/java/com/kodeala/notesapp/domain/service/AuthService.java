package com.kodeala.notesapp.domain.service;

import com.kodeala.notesapp.domain.dto.request.auth.LoginRequest;
import com.kodeala.notesapp.domain.dto.request.auth.RefreshTokenRequest;
import com.kodeala.notesapp.domain.dto.request.auth.RegisterRequest;
import com.kodeala.notesapp.domain.dto.response.auth.AuthResponse;
import com.kodeala.notesapp.domain.dto.response.UserResponse;
import com.kodeala.notesapp.domain.repository.UserRepository;
import com.kodeala.notesapp.domain.security.jwt.JwtService;
import com.kodeala.notesapp.persistence.entity.User;
import com.kodeala.notesapp.persistence.mapper.UserMapper;
import com.kodeala.notesapp.web.exception.ResourceNotFoundException;
import com.kodeala.notesapp.web.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        if(userRepository.getByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        if(userRepository.getByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already in use");
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User newUser = UserMapper.toUser(request);

        return Optional.of(UserMapper.toUserDTO(userRepository.create(newUser)));
    }

    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid credentials");
        }

        User user = userRepository.getByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid credentials"));

        String token = jwtService.getToken(user);
        String refreshToken = jwtService.getRefreshToken(user);

        user.setRefreshToken(refreshToken);
        userRepository.create(user);

        return AuthResponse.builder()
                .status("success")
                .message("Logged successfully")
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        User user = userRepository.getByUsername(jwtService.extractUsername(request.getRefreshToken()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if(!request.getRefreshToken().equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String newToken = jwtService.getToken(user);

        return new AuthResponse("success", "Token refreshed", newToken, null);
    }
}
