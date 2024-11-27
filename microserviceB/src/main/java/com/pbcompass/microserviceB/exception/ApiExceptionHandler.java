package com.pbcompass.microserviceB.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleObjectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
