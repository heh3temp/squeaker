package com.hamsterbusters.squeaker.exception;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, String status, LocalDateTime timestamp) {
}
