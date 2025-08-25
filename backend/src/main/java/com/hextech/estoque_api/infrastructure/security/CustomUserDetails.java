package com.hextech.estoque_api.infrastructure.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final Long clientId;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, Long clientId, String username, String password,
                             Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.clientId = clientId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getClientId() {
        return clientId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
