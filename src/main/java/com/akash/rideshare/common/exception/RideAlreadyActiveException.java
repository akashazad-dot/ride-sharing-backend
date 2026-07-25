package com.akash.rideshare.common.exception;

public class RideAlreadyActiveException extends RuntimeException {

    public RideAlreadyActiveException(String message) {
        super(message);
    }
}