package com.hextech.estoque_api.interfaces.handlers;

import com.hextech.estoque_api.application.exceptions.DeletionConflictException;
import com.hextech.estoque_api.application.exceptions.InvalidMovementTypeException;
import com.hextech.estoque_api.application.exceptions.ResourceNotFoundException;
import com.hextech.estoque_api.application.exceptions.UserAlreadyExistsException;
import com.hextech.estoque_api.infrastructure.security.exceptions.InvalidJwtAuthenticationException;
import com.hextech.estoque_api.interfaces.dtos.CustomError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<CustomError> handleInvalidJwt(InvalidJwtAuthenticationException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<CustomError> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomError error = new CustomError(Instant.now(), status.value(), "Usuário ou senha inválido!", request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(InvalidMovementTypeException.class)
    public ResponseEntity<CustomError> handleInvalidMovementType(InvalidMovementTypeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(DeletionConflictException.class)
    public ResponseEntity<CustomError> handleDeletionConflict(DeletionConflictException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomError> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
