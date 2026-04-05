package com.hextech.estoque_api.interfaces.dtos.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hextech.estoque_api.domain.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private Long userId;
    private String userName;
    private List<String> userRoles;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String refreshToken;
    private long tokenExpiresAt;

    public TokenDTO(String accessToken, String refreshToken, long expiresAt, User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userRoles = user.getRoleNames();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenExpiresAt = expiresAt;
    }
}
