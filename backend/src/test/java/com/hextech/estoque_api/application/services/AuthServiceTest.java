package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.UserFactory;
import com.hextech.estoque_api.domain.entities.User;
import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import com.hextech.estoque_api.infrastructure.security.jwt.JwtTokenProvider;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import com.hextech.estoque_api.interfaces.dtos.security.TokenDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider tokenProvider;
    @InjectMocks
    private AuthService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should authenticate user and return a token")
    void loginCase1() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("test@test.com", "123456");
        User user = UserFactory.createUser(1L);
        String tokenString = "testToken";
        TokenDTO tokenDTO = new TokenDTO(credentials.getUsername(), new Date(), new Date(System.currentTimeMillis() + 360000), "testToken");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenProvider.createAccessToken(any(), any(), anyLong())).thenReturn(tokenDTO);

        TokenDTO result = service.login(credentials);

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(tokenProvider, times(1)).createAccessToken(any(), any(), anyLong());
        assertNotNull(result);
        assertEquals(tokenString, result.getAccessToken());
        assertEquals(credentials.getUsername(), result.getUsername());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user does not exist")
    void loginCase2() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("test@test.com", "123456");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(UsernameNotFoundException.class, () -> {
            service.login(credentials);
        });

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(1)).findByEmail(anyString());
        verify(tokenProvider, times(0)).createAccessToken(any(), any(), anyLong());
        assertEquals("Usuário " + credentials.getUsername() + " não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw exception when authentication fails")
    void loginCase3() {
        AccountCredentialsDTO credentials = new AccountCredentialsDTO("test@test.com", "123456");

        when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("Authentication failed"));

        Exception thrown = assertThrows(BadCredentialsException.class, () -> {
            service.login(credentials);
        });

        verify(authenticationManager, times(1)).authenticate(any());
        verify(userRepository, times(0)).findByEmail(anyString());
        verify(tokenProvider, times(0)).createAccessToken(any(), any(), anyLong());
        assertEquals("Authentication failed", thrown.getMessage());
    }
}