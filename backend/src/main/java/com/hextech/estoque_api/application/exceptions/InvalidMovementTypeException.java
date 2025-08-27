package com.hextech.estoque_api.application.exceptions;

public class InvalidMovementTypeException extends RuntimeException {
    public InvalidMovementTypeException(String message) {
        super(message);
    }
}
