package com.kivanc.steamserver.core.exceptions;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException() {
    }

    public RecordNotFoundException(String message) {
        super(message);
    }
}
