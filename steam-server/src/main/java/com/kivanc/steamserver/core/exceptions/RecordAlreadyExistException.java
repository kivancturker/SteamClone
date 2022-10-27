package com.kivanc.steamserver.core.exceptions;

public class RecordAlreadyExistException extends RuntimeException {
    public RecordAlreadyExistException() {
    }

    public RecordAlreadyExistException(String message) {
        super(message);
    }
}
