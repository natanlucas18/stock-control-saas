package com.hextech.estoque_api.domain.exceptions;

public class ProductCodeAlreadyExistsException extends RuntimeException {
    public ProductCodeAlreadyExistsException(String message) {
        super(message);
    }
}
