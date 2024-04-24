package com.example.movie_server.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsernameExistsExceptionHandler {

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Object> handleUsernameExistsException(UsernameExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse() {
            @Override
            public HttpStatus getStatusCode() {
                return HttpStatus.CONFLICT;
            }

            @Override
            public ProblemDetail getBody() {
                return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
            }
        };
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}

