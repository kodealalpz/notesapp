package com.kodeala.notesapp.domain.dto.request.auth;

import lombok.Builder;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
