package com.coursework.car.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(final String message) {
        super(message);
    }
}
