package com.techdemy.exception;

import com.techdemy.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class RestApiExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, UsernameNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleResourceNotFound(ResourceNotFoundException ex, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();

        return errorMessage;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBadRequest(BadRequestException ex, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();

        return errorMessage;
    }

    @ExceptionHandler({AccessDeniedException.class, ForbiddenResourceException.class})
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage handleAccessDenied(Exception ex, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();

        return errorMessage;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage handleUnauthorizedException(Exception ex, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Incorrect username or password")
                .description(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();

        return errorMessage;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleGlobalException(Exception ex, WebRequest webRequest) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .description(webRequest.getDescription(false))
                .timestamp(new Date())
                .build();

        return errorMessage;
    }

}
