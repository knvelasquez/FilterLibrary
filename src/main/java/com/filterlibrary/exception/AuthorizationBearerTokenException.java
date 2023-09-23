package com.filterlibrary.exception;

public class AuthorizationBearerTokenException extends Exception {
    public AuthorizationBearerTokenException() {
        super("Authorization Bearer Token was not found in header request");
    }
}
