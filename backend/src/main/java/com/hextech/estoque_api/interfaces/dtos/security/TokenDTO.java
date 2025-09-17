package com.hextech.estoque_api.interfaces.dtos.security;

import java.util.Date;

public class TokenDTO {

    private String username;
    private Date created;
    private Date expiration;
    private String accessToken;

    public TokenDTO() {
    }

    public TokenDTO(String username, Date created, Date expiration, String accessToken) {
        this.username = username;
        this.created = created;
        this.expiration = expiration;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
