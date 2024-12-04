package com.pbcompass.microserviceA.exception;

import com.pbcompass.microserviceB.exception.ErrorMessage;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<com.pbcompass.microserviceB.exception.ErrorMessage> handleFeign(FeignException ex, HttpServletRequest request) {
        com.pbcompass.microserviceB.exception.ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
