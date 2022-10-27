package com.kivanc.steamserver.core.aop.exceptionhandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionResponse> handleGlobalException(Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), status);
        return ResponseEntity.status(status).body(response);
    }*/

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleValidationException(ValidationException exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), status);
        return ResponseEntity.status(status).body(response);
    }
}
