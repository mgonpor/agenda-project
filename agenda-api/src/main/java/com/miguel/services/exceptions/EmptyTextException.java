package com.miguel.services.exceptions;

public class EmptyTextException extends RuntimeException {
    public EmptyTextException(String message) {
        super(message);
    }
}
