package com.akash.rideshare.common.exception;

public class InvalidRideStateException extends RuntimeException {
    public InvalidRideStateException(String message) {
        super(message);
    }
}
