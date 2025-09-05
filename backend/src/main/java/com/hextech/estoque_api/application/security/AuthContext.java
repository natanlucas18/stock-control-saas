package com.hextech.estoque_api.application.security;

public interface AuthContext {

    Long getCurrentCompanyId();
    Long getCurrentUserId();
    boolean isAuthenticated();
}
