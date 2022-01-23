package com.hamsterbusters.squeaker.user;

public class InvalidValueException extends RuntimeException{
    public InvalidValueException(String errorMessage) {
        super(errorMessage);
    }
}
