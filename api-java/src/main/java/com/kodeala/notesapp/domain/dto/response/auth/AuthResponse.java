package com.kodeala.notesapp.domain.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String status;
    private String message;
    private String token;
    private String refreshToken;
}
