package com.kodeala.notesapp.web.controller;

import com.kodeala.notesapp.domain.dto.request.LoginRequest;
import com.kodeala.notesapp.domain.dto.request.RegisterRequest;
import com.kodeala.notesapp.domain.dto.response.ApiResponse;
import com.kodeala.notesapp.domain.dto.response.auth.AuthResponse;
import com.kodeala.notesapp.domain.dto.response.UserResponse;
import com.kodeala.notesapp.domain.security.jwt.JwtService;
import com.kodeala.notesapp.domain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest request) {
        ApiResponse<UserResponse> response = new ApiResponse<>();

        return authService.register(request)
                .map(userResponse -> {
                    response.setStatus("success");
                    response.setMessage("Request processed successfully");
                    response.setData(userResponse);

                    return new ResponseEntity<>(response, HttpStatus.CREATED);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

//    @PostMapping("/refresh")
//    @PostMapping("/logout")
//    @GetMapping("/me")
}
