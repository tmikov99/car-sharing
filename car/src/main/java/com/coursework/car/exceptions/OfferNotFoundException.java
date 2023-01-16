package com.coursework.car.exceptions;

public class OfferNotFoundException extends RuntimeException {
    public OfferNotFoundException(final String message) {
        super(message);
    }
}
