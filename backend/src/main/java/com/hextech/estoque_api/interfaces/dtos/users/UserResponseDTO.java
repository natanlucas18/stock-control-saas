package com.hextech.estoque_api.interfaces.dtos.users;

import com.hextech.estoque_api.domain.entities.user.User;

import java.util.List;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<String> roles;

    public UserResponseDTO() {
    }

    public UserResponseDTO(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.roles = entity.getRoleNames();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
