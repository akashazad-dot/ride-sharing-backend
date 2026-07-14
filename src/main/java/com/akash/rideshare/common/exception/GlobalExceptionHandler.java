package com.akash.rideshare.common.exception;

import com.akash.rideshare.common.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {

        ApiResponse<Void> apiResponse = ApiResponse.<Void>builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(apiResponse);
    }
}
