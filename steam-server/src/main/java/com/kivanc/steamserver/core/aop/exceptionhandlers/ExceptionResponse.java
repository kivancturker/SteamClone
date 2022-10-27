package com.kivanc.steamserver.core.aop.exceptionhandlers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private String message;
    private HttpStatus status;
    private int statusCode;

    public ExceptionResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        this.statusCode = status.value();
    }
}
