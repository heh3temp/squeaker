package com.hamsterbusters.squeaker.post;

public class IllegalOperationException extends RuntimeException{
    public IllegalOperationException(String errorMessage) {
        super(errorMessage);
    }
}
