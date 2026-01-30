package com.miguel.services.exceptions;

public class UserNotFoundException extends GlobalNotFound {
    public UserNotFoundException(String message) {
        super(message);
    }
}
