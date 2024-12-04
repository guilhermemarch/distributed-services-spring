package com.pbcompass.microserviceA.exception;

import com.pbcompass.microserviceB.exception.ErrorMessage;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorMessage> handleFeign(FeignException ex, HttpServletRequest request) {
        switch (ex.status()) {
            case 404:
                ErrorMessage errorMessage = new ErrorMessage(
                        request.getRequestURI(),
                        request.getMethod(),
                        HttpStatus.NOT_FOUND.value(),
                        "Not Found",
                        ex.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);

            case 400:
                errorMessage = new ErrorMessage(
                        request.getRequestURI(),
                        request.getMethod(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        ex.getMessage());
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
            default:
                errorMessage = new ErrorMessage(
                        request.getRequestURI(),
                        request.getMethod(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        ex.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Invalid arguments"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<ErrorMessage> handleUnexpectedTypeException(UnexpectedTypeException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorMessage> handleInvalidDataException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Invalid data"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


}
