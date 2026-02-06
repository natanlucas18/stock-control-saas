package com.hextech.estoque_api.interfaces.dtos.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hextech.estoque_api.domain.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {

    private Long userId;
    private String userName;
    private List<String> userRoles;
    private String accessToken;
    private String refreshToken;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiresAt;

    public TokenDTO(String accessToken, String refreshToken, Date createdAt, Date expiresAt, User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userRoles = user.getRoleNames();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.createdAt = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.expiresAt = expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
