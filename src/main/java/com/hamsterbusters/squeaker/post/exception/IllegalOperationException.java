package com.hamsterbusters.squeaker.post.exception;

public class IllegalOperationException extends IllegalStateException {
    public IllegalOperationException(String errorMessage) {
        super(errorMessage);
    }
}
