package com.hamsterbusters.squeaker.user.exception;

public class InvalidUserDataException extends IllegalArgumentException {
    public InvalidUserDataException(String errorMessage) {
        super(errorMessage);
    }

    public InvalidUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
