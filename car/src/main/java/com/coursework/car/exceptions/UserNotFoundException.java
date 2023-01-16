package com.coursework.car.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(final String message) {
        super(message);
    }
}
