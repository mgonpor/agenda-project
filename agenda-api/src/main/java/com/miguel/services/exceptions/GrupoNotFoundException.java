package com.miguel.services.exceptions;

public class GrupoNotFoundException extends RuntimeException {
    public GrupoNotFoundException(String message) {
        super(message);
    }
}
