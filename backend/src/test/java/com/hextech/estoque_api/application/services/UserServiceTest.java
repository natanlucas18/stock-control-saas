package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.UserFactory;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.exceptions.UserAlreadyExistsException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.RoleRepository;
import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import com.hextech.estoque_api.interfaces.dtos.users.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should load user by username")
    void loadUserByUsernameCase1() {
        Long userId = 1L;
        String username = "test@test.com";

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserFactory.createUser(userId)));

        var userDetails = service.loadUserByUsername(username);

        verify(repository, times(1)).findByEmail(anyString());

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void loadUserByUsernameCase2() {
        String username = "usernameNotFound@test.com";

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(username);
        });

        verify(repository, times(1)).findByEmail(anyString());
        assertEquals("Usuário " + username + " não encontrado ou desabilitado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user disabled")
    void loadUserByUsernameCase3() {
        String username = "test@test.com";
        User user = UserFactory.createUser(1L);
        user.setEnabled(false);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));

        Exception thrown = assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(username);
        });

        verify(repository, times(1)).findByEmail(anyString());
        assertEquals("Usuário " + username + " não encontrado ou desabilitado.", thrown.getMessage());
    }


    @Test
    @DisplayName("Should create a new user successfully")
    void createNewUserCase1() {
        Long userId = 1L;
        Long companyId = 1L;
        String username = "test@test.com";
        UserRequestDTO requestDTO = UserFactory.createUserRequestDTO(userId);

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(roleRepository.findAllById(any())).thenReturn(UserFactory.createUser(userId).getRoles());
        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");
        when(repository.save(any())).thenReturn(UserFactory.createUser(userId));

        var userDetails = service.createNewUser(requestDTO, companyId);

        verify(repository, times(1)).findByEmail(anyString());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).findAllById(any());
        verify(repository, times(1)).save(any());
        verify(passwordEncoder, times(1)).encode(anyString());
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getEmail());
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when email already exists")
    void createNewUserCase2() {
        Long userId = 1L;
        Long companyId = 1L;
        UserRequestDTO requestDTO = UserFactory.createUserRequestDTO(userId);

        when(repository.findByEmail(anyString())).thenReturn(Optional.of(UserFactory.createUser(userId)));

        Exception thrown = assertThrows(UserAlreadyExistsException.class, () -> {
            service.createNewUser(requestDTO, companyId);
        });

        verify(repository, times(1)).findByEmail(anyString());
        verify(companyRepository, times(0)).findById(anyLong());
        verify(roleRepository, times(0)).findAllById(any());
        verify(repository, times(0)).save(any());
        assertEquals("Já existe um usuário com este email.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found")
    void createNewUserCase3() {
        Long userId = 1L;
        Long companyId = 1L;
        UserRequestDTO requestDTO = UserFactory.createUserRequestDTO(userId);

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception thrown = assertThrows(com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException.class, () -> {
            service.createNewUser(requestDTO, companyId);
        });

        verify(repository, times(1)).findByEmail(anyString());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(0)).findAllById(any());
        verify(repository, times(0)).save(any());
        assertEquals("Empresa não encontrada.", thrown.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when one or more roles not found")
    void createNewUserCase4() {
        Long userId = 1L;
        Long companyId = 1L;
        UserRequestDTO requestDTO = UserFactory.createUserRequestDTO(userId);

        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(CompanyFactory.createCompany(companyId)));
        when(roleRepository.findAllById(any())).thenReturn(UserFactory.createUser(userId).getRoles().subList(0, 1));

        Exception thrown = assertThrows(ResourceNotFoundException.class, () -> {
            service.createNewUser(requestDTO, companyId);
        });

        verify(repository, times(1)).findByEmail(anyString());
        verify(companyRepository, times(1)).findById(anyLong());
        verify(roleRepository, times(1)).findAllById(any());
        verify(repository, times(0)).save(any());
        assertEquals("Uma ou mais funções não foram encontradas.", thrown.getMessage());
    }
}