package com.miguel.services.exceptions;

public class GlobalNotFound extends RuntimeException {
    public GlobalNotFound(String message) {
        super(message);
    }
}
