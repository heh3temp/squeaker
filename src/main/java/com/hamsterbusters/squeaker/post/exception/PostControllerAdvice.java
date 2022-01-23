package com.hamsterbusters.squeaker.post.exception;

import com.hamsterbusters.squeaker.exception.ExceptionResponse;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class PostControllerAdvice {

    @ExceptionHandler(IllegalOperationException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalOperationException(IllegalOperationException exception) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        new ExceptionResponse(exception.getMessage(), HttpStatus.FORBIDDEN.getReasonPhrase(), LocalDateTime.now())
                );
    }

}
