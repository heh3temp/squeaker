package com.hamsterbusters.squeaker.exception;

import java.time.LocalDateTime;

public record UnexpectedErrorResponse(String status, LocalDateTime timestamp) {
}
