package com.kivanc.steamserver.customer.exceptions;

public class CustomerAlreadyExistException extends  RuntimeException{
    public CustomerAlreadyExistException() {
    }

    public CustomerAlreadyExistException(String message) {
        super(message);
    }
}
