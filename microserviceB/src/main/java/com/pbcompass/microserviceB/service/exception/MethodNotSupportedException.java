package com.pbcompass.microserviceB.service.exception;

public class MethodNotSupportedException extends RuntimeException {

    public MethodNotSupportedException(String message) {
        super(message);
    }
}
