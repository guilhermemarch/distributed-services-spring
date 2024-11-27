package com.pbcompass.microserviceB.service.exception;

public class NoPostsFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public NoPostsFoundException(String message) {
    super(message);
  }
}