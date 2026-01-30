package com.miguel.web.config;

import com.miguel.services.exceptions.GlobalBadRequest;
import com.miguel.services.exceptions.GlobalNotFound;
import com.miguel.services.exceptions.WrongUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalNotFound.class)
    public ResponseEntity<String> handleNotFound(GlobalNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(GlobalBadRequest.class)
    public ResponseEntity<String> handleBadRequest(GlobalBadRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(WrongUserException.class)
    public ResponseEntity<String> handleWrongUser(WrongUserException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

}
