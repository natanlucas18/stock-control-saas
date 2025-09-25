package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import com.hextech.estoque_api.infrastructure.security.jwt.JwtTokenProvider;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import com.hextech.estoque_api.interfaces.dtos.security.TokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public TokenDTO login(AccountCredentialsDTO credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
        var user = userRepository.findByEmail(credentials.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário " + credentials.getUsername() + " não encontrado."));
        return tokenProvider.createAccessToken(user);
    }
}
