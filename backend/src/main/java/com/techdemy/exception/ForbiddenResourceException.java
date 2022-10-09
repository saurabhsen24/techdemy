package com.techdemy.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ForbiddenResourceException extends RuntimeException {

    public ForbiddenResourceException(String message) {
        super(message);
    }
}