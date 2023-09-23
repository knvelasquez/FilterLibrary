package com.filterlibrary.exception;

public class JwtBasedAuthenticationException extends Exception {
    public JwtBasedAuthenticationException() {
        super("Jwt based authentication was not successful because no claims were found");
    }
}
