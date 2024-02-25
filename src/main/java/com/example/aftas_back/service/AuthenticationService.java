package com.example.aftas_back.service;

import com.example.aftas_back.dto.request.AuthenticationRequest;
import com.example.aftas_back.dto.request.RegisterRequest;
import com.example.aftas_back.handler.response.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

}
