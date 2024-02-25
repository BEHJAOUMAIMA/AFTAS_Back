package com.example.aftas_back.security.jwt;

import com.example.aftas_back.domain.RefreshToken;
import com.example.aftas_back.dto.request.RefreshTokenRequest;
import com.example.aftas_back.handler.response.RefreshTokenResponse;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {
    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);

}
