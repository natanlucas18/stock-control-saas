package com.hextech.estoque_api.infrastructure.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthenticationException extends AuthenticationException {
    public InvalidJwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public InvalidJwtAuthenticationException(String message) {
        super(message);
    }
}
