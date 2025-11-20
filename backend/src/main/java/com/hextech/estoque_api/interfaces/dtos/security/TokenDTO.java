package com.hextech.estoque_api.interfaces.dtos.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hextech.estoque_api.domain.entities.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class TokenDTO {

    private Long userId;
    private String userName;
    private List<String> userRoles;
    private String accessToken;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime expiresAt;

    public TokenDTO() {
    }

    public TokenDTO(String accessToken, Date createdAt, Date expiresAt, User user) {
        this.userId = user.getId();
        this.userName = user.getName();
        this.userRoles = user.getRoleNames();
        this.accessToken = accessToken;
        this.createdAt = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.expiresAt = expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
