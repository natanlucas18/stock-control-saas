package com.hextech.estoque_api.application.services;

import com.hextech.estoque_api.application.tests.CompanyFactory;
import com.hextech.estoque_api.application.tests.UserFactory;
import com.hextech.estoque_api.domain.entities.company.Company;
import com.hextech.estoque_api.domain.entities.role.Role;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.domain.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.domain.exceptions.UserAlreadyExistsException;
import com.hextech.estoque_api.infrastructure.repositories.CompanyRepository;
import com.hextech.estoque_api.infrastructure.repositories.RoleRepository;
import com.hextech.estoque_api.infrastructure.repositories.UserRepository;
import com.hextech.estoque_api.interfaces.dtos.users.UserRequestDTO;
import com.hextech.estoque_api.interfaces.dtos.users.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private Long existingUserId;
    private Long nonExistingUserId;
    private Long existingCompanyId;
    private Long nonExistingCompanyId;
    private User user;
    private Company company;
    private UserRequestDTO userRequestDTO;
    private String existingEmail;
    private String nonExistingEmail;
    private String password;

    @BeforeEach
    void setUp() {
        existingUserId = 1L;
        nonExistingUserId = 99L;
        existingCompanyId = 1L;
        nonExistingCompanyId = 99L;
        existingEmail = "test@test.com";
        nonExistingEmail = "nonexistent@test.com";
        password = "password";

        company = CompanyFactory.createCompany(existingCompanyId);
        user = UserFactory.createUser(existingUserId);
        userRequestDTO = UserFactory.createUserRequestDTO(existingUserId);
    }

    @Test
    @DisplayName("Should load user by username successfully")
    void loadUserByUsername_ShouldReturnUserDetails() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(existingEmail);

        assertNotNull(userDetails);
        assertEquals(existingEmail, userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(existingEmail);
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(nonExistingEmail));

        assertEquals("Usuário " + nonExistingEmail + " não encontrado ou desabilitado.", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user is disabled")
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserIsDisabled() {
        user.setEnabled(false);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () ->
                userService.loadUserByUsername(existingEmail));

        assertEquals("Usuário " + existingEmail + " não encontrado ou desabilitado.", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(existingEmail);
    }

    @Test
    @DisplayName("Should create a new user successfully")
    void createNewUser_ShouldReturnUserResponseDTO() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(roleRepository.findAllById(anyIterable())).thenReturn(user.getRoles());
        when(passwordEncoder.encode(anyString())).thenReturn(password);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO result = userService.createNewUser(userRequestDTO, existingCompanyId);

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(userRequestDTO.getEmail(), result.getEmail());
        verify(userRepository, times(1)).findByEmail(userRequestDTO.getEmail());
        verify(companyRepository, times(1)).findById(eq(existingCompanyId));
        verify(roleRepository, times(1)).findAllById(anyIterable());
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw UserAlreadyExistsException when email already exists during creation")
    void createNewUser_ShouldThrowUserAlreadyExistsException_WhenEmailExists() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () ->
                userService.createNewUser(userRequestDTO, existingCompanyId));

        assertEquals("Já existe um usuário com este email.", exception.getMessage());
        verify(companyRepository, times(1)).findById(existingCompanyId);
        verify(userRepository, times(1)).findByEmail(userRequestDTO.getEmail());
        verify(roleRepository, never()).findAllById(anyIterable());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found during creation")
    void createNewUser_ShouldThrowResourceNotFoundException_WhenCompanyNotFound() {
        when(companyRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.createNewUser(userRequestDTO, nonExistingCompanyId));

        assertEquals("Empresa não encontrada.", exception.getMessage());
        verify(companyRepository, times(1)).findById(eq(nonExistingCompanyId));
        verify(userRepository, never()).findByEmail(userRequestDTO.getEmail());
        verify(roleRepository, never()).findAllById(anyIterable());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when roles not found during creation")
    void createNewUser_ShouldThrowResourceNotFoundException_WhenRolesNotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyRepository.findById(anyLong())).thenReturn(Optional.of(company));
        when(roleRepository.findAllById(anyIterable())).thenReturn(List.of()); // No roles found

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.createNewUser(userRequestDTO, existingCompanyId));

        assertEquals("Uma ou mais funções não foram encontradas.", exception.getMessage());
        verify(userRepository, times(1)).findByEmail(userRequestDTO.getEmail());
        verify(companyRepository, times(1)).findById(eq(existingCompanyId));
        verify(roleRepository, times(1)).findAllById(userRequestDTO.getRolesId());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should get current user successfully")
    void getMe_ShouldReturnUserResponseDTO() {
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.getMe(existingUserId, existingCompanyId);

        assertNotNull(result);
        assertEquals(existingUserId, result.getId());
        verify(userRepository, times(1)).findByIdAndCompanyId(eq(existingUserId), eq(existingCompanyId));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when current user not found")
    void getMe_ShouldThrowResourceNotFoundException_WhenCurrentUserNotFound() {
        when(userRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () ->
                userService.getMe(nonExistingUserId, existingCompanyId));

        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(userRepository, times(1)).findByIdAndCompanyId(eq(nonExistingUserId), eq(existingCompanyId));
    }
}
