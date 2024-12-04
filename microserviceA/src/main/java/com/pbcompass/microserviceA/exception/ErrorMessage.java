package com.pbcompass.microserviceA.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String error;
    private String message;
}