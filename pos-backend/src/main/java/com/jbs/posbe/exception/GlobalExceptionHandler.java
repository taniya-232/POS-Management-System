package com.jbs.posbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jbs.posbe.dto.ManagedApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	// =========================================================
    // RESOURCE NOT FOUND
    // =========================================================
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ManagedApiResponse<Object>> handleNotFound(
            ResourceNotFoundException ex) {

    	ManagedApiResponse<Object> response =
                new ManagedApiResponse<>(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage(),
                        null
                );

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // =========================================================
    // RESOURCE DELETED
    // =========================================================
    @ExceptionHandler(ResourceDeletedException.class)
    public ResponseEntity<ManagedApiResponse<Object>> handleDeleted(
            ResourceDeletedException ex) {

    	ManagedApiResponse<Object> response =
                new ManagedApiResponse<>(
                        HttpStatus.GONE.value(),
                        ex.getMessage(),
                        null
                );

        return ResponseEntity.status(HttpStatus.GONE)
                .body(response);
    }

    // =========================================================
    // VALIDATION ERRORS
    // =========================================================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ManagedApiResponse<Object>> handleValidation(
            MethodArgumentNotValidException ex) {

        String errorMessage =
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getField()
                                        + ": "
                                        + error.getDefaultMessage())
                        .findFirst()
                        .orElse("Validation failed");

        ManagedApiResponse<Object> response =
                new ManagedApiResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        errorMessage,
                        null
                );

        return ResponseEntity.badRequest()
                .body(response);
    }

    // =========================================================
    // GENERIC EXCEPTION
    // =========================================================
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ManagedApiResponse<Object>> handleGeneric(
            Exception ex) {

    	ManagedApiResponse<Object> response =
                new ManagedApiResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        ex.getMessage(),
                        null
                );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
