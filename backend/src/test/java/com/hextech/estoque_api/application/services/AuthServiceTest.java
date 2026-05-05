package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.UserFactory;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import com.hextech.estoque_api.infrastructure.security.jwt.JwtTokenProvider;
import com.hextech.estoque_api.interfaces.dtos.security.AccountCredentialsDTO;
import com.hextech.estoque_api.interfaces.dtos.security.TokenDTO;
import com.hextech.estoque_api.interfaces.dtos.users.SessionDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthService authService;

    private User testUser;
    private AccountCredentialsDTO credentialsDTO;
    private TokenDTO tokenDTO;
    long expiresAt;

    @BeforeEach
    void setUp() {
        testUser = UserFactory.createUser(1L);
        expiresAt = 604800L;

        credentialsDTO = new AccountCredentialsDTO("test@example.com", "password");
        tokenDTO = new TokenDTO("accessToken", "refreshToken", expiresAt, testUser);
    }

    @Test
    @DisplayName("Should return current session when valid data is provided")
    void getCurrentSession_Success() {
        when(tokenProvider.resolveAccessToken(request)).thenReturn("accessToken");
        when(tokenProvider.extractRemainingTime("accessToken")).thenReturn(expiresAt);
        when(userRepository.findByIdAndCompanyId(1L, 1L)).thenReturn(Optional.of(testUser));

        SessionDTO result = authService.getCurrentSession(request, 1L, 1L);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.userId());
        assertEquals(testUser.getName(), result.userName());
        assertEquals(testUser.getRoleNames(), result.userRoles());
        assertEquals(expiresAt, result.tokenExpiresAt());

        verify(tokenProvider, times(1)).resolveAccessToken(request);
        verify(tokenProvider, times(1)).extractRemainingTime("accessToken");
        verify(userRepository, times(1)).findByIdAndCompanyId(1L, 1L);
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user is not found for current session")
    void getCurrentSession_UserNotFound() {
        when(tokenProvider.resolveAccessToken(request)).thenReturn("accessToken");
        when(tokenProvider.extractRemainingTime("accessToken")).thenReturn(3600L);
        when(userRepository.findByIdAndCompanyId(1L, 1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                authService.getCurrentSession(request, 1L, 1L));

        assertEquals("Usuário não encontrado", exception.getMessage());

        verify(tokenProvider, times(1)).resolveAccessToken(request);
        verify(tokenProvider, times(1)).extractRemainingTime("accessToken");
        verify(userRepository, times(1)).findByIdAndCompanyId(1L, 1L);
    }

    @Test
    @DisplayName("Should return TokenDTO on successful login")
    void login_Success() {
        when(userRepository.findByEmail(credentialsDTO.getUsername())).thenReturn(Optional.of(testUser));
        when(tokenProvider.createAccessToken(testUser)).thenReturn(tokenDTO);

        TokenDTO result = authService.login(credentialsDTO);

        assertNotNull(result);
        assertEquals(tokenDTO, result);

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(credentialsDTO.getUsername());
        verify(tokenProvider, times(1)).createAccessToken(testUser);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user is not found during login")
    void login_UserNotFound() {
        when(userRepository.findByEmail(credentialsDTO.getUsername())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                authService.login(credentialsDTO));

        assertEquals("Usuário " + credentialsDTO.getUsername() + " não encontrado.", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(credentialsDTO.getUsername());
        verify(tokenProvider, never()).createAccessToken(any(User.class));
    }

    @Test
    @DisplayName("Should return new TokenDTO on successful refresh token")
    void refreshToken_Success() {
        when(tokenProvider.resolveRefreshToken(request)).thenReturn("refreshToken");
        when(tokenProvider.extractUsername("refreshToken")).thenReturn(testUser.getEmail());
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
        when(tokenProvider.refreshToken(testUser, "refreshToken")).thenReturn(tokenDTO);

        TokenDTO result = authService.refreshToken(request);

        assertNotNull(result);
        assertEquals(tokenDTO, result);

        verify(tokenProvider, times(1)).resolveRefreshToken(request);
        verify(tokenProvider, times(1)).extractUsername("refreshToken");
        verify(userRepository, times(1)).findByEmail(testUser.getEmail());
        verify(tokenProvider, times(1)).refreshToken(testUser, "refreshToken");
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user is not found during refresh token")
    void refreshToken_UserNotFound() {
        when(tokenProvider.resolveRefreshToken(request)).thenReturn("refreshToken");
        when(tokenProvider.extractUsername("refreshToken")).thenReturn(testUser.getEmail());
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                authService.refreshToken(request));

        assertEquals("Usuário " + testUser.getEmail() + " não encontrado.", exception.getMessage());

        verify(tokenProvider, times(1)).resolveRefreshToken(request);
        verify(tokenProvider, times(1)).extractUsername("refreshToken");
        verify(userRepository, times(1)).findByEmail(testUser.getEmail());
        verify(tokenProvider, never()).refreshToken(any(User.class), anyString());
    }
}
