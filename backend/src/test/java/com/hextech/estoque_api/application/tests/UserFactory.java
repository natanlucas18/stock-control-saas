package com.hextech.estoque_api.application.tests;

import com.hextech.estoque_api.domain.entities.role.Role;
import com.hextech.estoque_api.domain.entities.user.User;
import com.hextech.estoque_api.interfaces.dtos.users.UserRequestDTO;

import java.util.List;

public class UserFactory {

    public static User createUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("Test User");
        user.setEmail("test@test.com");
        user.setPassword("encryptedPassword");
        user.setEnabled(true);
        user.setRoles(List.of(new Role(1L, "ROLE_ADMIN"), new Role(2L, "ROLE_OPERATOR")));
        user.setCompany(CompanyFactory.createCompany(1L));
        return user;
    }

    public static UserRequestDTO createUserRequestDTO(Long id) {
        User user = createUser(id);
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword("encryptedPassword");
        dto.setRolesId(user.getRoles().stream().map(Role::getId).toList());
        return dto;
    }
}