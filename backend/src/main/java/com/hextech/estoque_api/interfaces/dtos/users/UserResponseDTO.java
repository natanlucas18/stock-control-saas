package com.hextech.estoque_api.interfaces.dtos.users;

import com.hextech.estoque_api.domain.entities.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<String> roles;

    public UserResponseDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.roles = entity.getRoleNames();
    }
}