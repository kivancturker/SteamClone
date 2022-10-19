package com.kivanc.steamserver.customer.exceptions;

public class CustomerDoesNotExistException extends RuntimeException {
    public CustomerDoesNotExistException() {
    }

    public CustomerDoesNotExistException(String message) {
        super(message);
    }
}
