package com.techdemy.exception;

public class ResourceNotFoundException extends RuntimeException {

    private String message;

    public ResourceNotFoundException() {
        super("Resource not found");
    }
    public ResourceNotFoundException(String message ) {
        super(message);
    }

}