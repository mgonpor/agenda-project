package com.miguel.services.exceptions;

public class EmptyTextException extends GlobalBadRequest {
    public EmptyTextException(String message) {
        super(message);
    }
}
