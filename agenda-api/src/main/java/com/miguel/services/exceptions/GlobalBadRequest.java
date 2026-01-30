package com.miguel.services.exceptions;

public class GlobalBadRequest extends RuntimeException {
    public GlobalBadRequest(String message) {
        super(message);
    }
}
