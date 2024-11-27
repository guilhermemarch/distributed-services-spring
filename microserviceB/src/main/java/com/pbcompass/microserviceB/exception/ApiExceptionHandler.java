package com.pbcompass.microserviceB.exception;

import com.pbcompass.microserviceB.service.exception.NoPostsFoundException;
import com.pbcompass.microserviceB.service.exception.ObjectNotFoundException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
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


    @ExceptionHandler(NoPostsFoundException.class)
    public ResponseEntity<ErrorMessage> handleNoPostsFoundException(NoPostsFoundException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.NOT_FOUND.value(),
                "No posts Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request.getRequestURI(),
                request.getMethod(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Invalid data"
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
                "Invalid data"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
