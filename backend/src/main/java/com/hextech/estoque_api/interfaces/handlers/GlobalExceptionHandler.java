package com.hextech.estoque_api.interfaces.handlers;

import com.hextech.estoque_api.domain.exceptions.*;
import com.hextech.estoque_api.infrastructure.security.exceptions.InvalidJwtAuthenticationException;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.errors.CustomError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public ResponseEntity<StandardResponse<?>> handleInvalidJwt(InvalidJwtAuthenticationException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardResponse<?>> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        CustomError error = new CustomError(Instant.now(), status.value(), "Usuário ou senha inválido!", request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardResponse<?>> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardResponse<?>> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(InvalidMovementTypeException.class)
    public ResponseEntity<StandardResponse<?>> handleInvalidMovementType(InvalidMovementTypeException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(DeletionConflictException.class)
    public ResponseEntity<StandardResponse<?>> handleDeletionConflict(DeletionConflictException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardResponse<?>> handleUserAlreadyExists(UserAlreadyExistsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(InvalidUnitMeasureException.class)
    public ResponseEntity<StandardResponse<?>> handleInvalidUnitMeasure(InvalidUnitMeasureException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }
}
