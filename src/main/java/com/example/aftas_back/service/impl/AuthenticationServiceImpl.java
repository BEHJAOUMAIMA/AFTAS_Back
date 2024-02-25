package com.example.aftas_back.service.impl;

import com.example.aftas_back.domain.User;
import com.example.aftas_back.domain.enums.Role;
import com.example.aftas_back.domain.enums.TokenType;
import com.example.aftas_back.dto.request.AuthenticationRequest;
import com.example.aftas_back.dto.request.RegisterRequest;
import com.example.aftas_back.handler.exception.OperationException;
import com.example.aftas_back.handler.response.AuthenticationResponse;
import com.example.aftas_back.repository.UserRepository;
import com.example.aftas_back.security.jwt.JwtService;
import com.example.aftas_back.security.jwt.RefreshTokenService;
import com.example.aftas_back.service.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (!request.getPassword().equals(request.getConfirmedPassword())) {
            throw new OperationException("Confirmed password does not match");
        }
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new OperationException("User with this email already exists");
        }

        var user = User.builder()
                .name(request.getName())
                .familyName(request.getFamilyName())
                .nationality(request.getNationality())
                .identityDocumentType(request.getIdentityDocumentType())
                .identityNumber(request.getIdentityNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.MEMBER)
                .accessionDate(LocalDate.now().atStartOfDay())
                .status(false)
                .build();

        user = userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());

        var roles = Collections.singletonList(user.getRole().name());


        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .email(user.getEmail())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .tokenType( TokenType.BEARER.name())
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email   or password."));
        var jwt = jwtService.generateToken(user);
        var refreshToken = refreshTokenService.createRefreshToken(user.getId());
        var roles = Collections.singletonList(user.getRole().name());
        var permissions = user.getRole().getAuthorities();
        return AuthenticationResponse.builder()
                .accessToken(jwt)
                .email(user.getEmail())
                .id(user.getId())
                .refreshToken(refreshToken.getToken())
                .roles(roles)
                .authorities(permissions)
                .tokenType( TokenType.BEARER.name())
                .build();
    }
}
