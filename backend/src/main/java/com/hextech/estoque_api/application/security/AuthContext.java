package com.hextech.estoque_api.application.security;

public interface AuthContext {

    Long getCurrentClientId();
    Long getCurrentUserId();
    boolean isAuthenticated();
}
