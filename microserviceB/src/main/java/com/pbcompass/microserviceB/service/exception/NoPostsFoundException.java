package com.pbcompass.microserviceB.service.exception;

public class NoPostsFoundException extends RuntimeException {

    public NoPostsFoundException(String message) {
        super(message);
    }
}