package com.hextech.estoque_api.application.exceptions;

public class DeletionConflictException extends RuntimeException {
    public DeletionConflictException(String message) {
        super(message);
    }
}
