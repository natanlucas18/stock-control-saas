package com.hextech.estoque_api.interfaces.handlers;

import com.hextech.estoque_api.domain.exceptions.*;
import com.hextech.estoque_api.interfaces.dtos.StarndardResponse.StandardResponse;
import com.hextech.estoque_api.interfaces.dtos.errors.CustomError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<StandardResponse<?>> handleResourceNotFound(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomError error = new CustomError(Instant.now().toString(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler({IllegalArgumentException.class, InvalidMovementTypeException.class})
    public ResponseEntity<StandardResponse<?>> handleIllegalArgument(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError error = new CustomError(Instant.now().toString(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler({DeletionConflictException.class, UserAlreadyExistsException.class,
            ProductCodeAlreadyExistsException.class, BusinessException.class})
    public ResponseEntity<StandardResponse<?>> handleDeletionConflict(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        CustomError error = new CustomError(Instant.now().toString(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(new StandardResponse<>(false, List.of(error)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardResponse<?>> handleFieldError(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        List<CustomError> errors = new ArrayList<>();
        for (var fieldError : ex.getFieldErrors()) {
            CustomError error = new CustomError(Instant.now().toString(), status.value(), fieldError.getDefaultMessage(), request.getRequestURI());
            errors.add(error);
        }
        return ResponseEntity.status(status).body(new StandardResponse<>(false, errors));
    }
}
