package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import com.hextech.estoque_api.infrastructure.security.jwt.JwtTokenProvider;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import com.hextech.estoque_api.interfaces.dtos.security.TokenDTO;
import com.hextech.estoque_api.interfaces.dtos.users.SessionDTO;
import jakarta.servlet.http.HttpServletRequest;
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

    public SessionDTO getCurrentSession(HttpServletRequest request, Long userId, Long companyId) {
        var token = tokenProvider.resolveAccessToken(request);
        long remainingTime = tokenProvider.extractRemainingTime(token);
        var user = userRepository.findByIdAndCompanyId(userId, companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return new SessionDTO(user.getId(), user.getName(), user.getRoleNames(), remainingTime);
    }

    public TokenDTO login(AccountCredentialsDTO credentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
        var user = userRepository.findByEmail(credentials.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário " + credentials.getUsername() + " não encontrado."));
        return tokenProvider.createAccessToken(user);
    }

    public TokenDTO refreshToken(String username, HttpServletRequest request) {
        String refreshToken = tokenProvider.resolveRefreshToken(request);
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado."));
        return tokenProvider.refreshToken(user, refreshToken);
    }
}
