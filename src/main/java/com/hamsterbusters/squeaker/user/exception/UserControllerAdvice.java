package com.hamsterbusters.squeaker.user.exception;

import com.hamsterbusters.squeaker.exception.ExceptionResponse;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity
                .unprocessableEntity()
                .body(
                        new ExceptionResponse(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(), LocalDateTime.now())
                );
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ExceptionResponse(exception.getMessage(), HttpStatus.NOT_FOUND.getReasonPhrase(), LocalDateTime.now())
                );
    }

}
