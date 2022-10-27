package com.kivanc.steamserver.core.aop.exceptionhandlers;

import com.kivanc.steamserver.core.exceptions.RecordAlreadyExistException;
import com.kivanc.steamserver.core.exceptions.RecordNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RecordExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionResponse> handleRecordNotFoundException(RecordNotFoundException exception) {
        log.error(exception.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), status);
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(RecordAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handleRecordAlreadyExistException(RecordAlreadyExistException exception) {
        log.error(exception.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), status);
        return ResponseEntity.status(status).body(response);
    }

}
