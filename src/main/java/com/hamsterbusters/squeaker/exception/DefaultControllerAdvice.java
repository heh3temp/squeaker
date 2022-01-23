package com.hamsterbusters.squeaker.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<UnexpectedErrorResponse> handleUnexpectedException(RuntimeException exception) {
        log.error("Unexpected Error: {}", ExceptionUtils.getStackTrace(exception));
        return ResponseEntity
                .internalServerError()
                .body(
                        new UnexpectedErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), LocalDateTime.now())
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception) {
        return ResponseEntity
                .badRequest()
                .body(
                        new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.getReasonPhrase(), LocalDateTime.now())
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRequestException(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(), LocalDateTime.now())
                );
    }

}
