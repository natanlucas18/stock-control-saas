package com.hextech.estoque_api.domain.exceptions;

public class InvalidUnitMeasureException extends RuntimeException {
    public InvalidUnitMeasureException(String message) {
        super(message);
    }
}
