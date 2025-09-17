package com.hextech.estoque_api.domain.exceptions;

public class DeletionConflictException extends RuntimeException {
    public DeletionConflictException(String message) {
        super(message);
    }
}
