package com.filterlibrary.exception;

public class FilterNotFoundException extends Exception {
    public FilterNotFoundException(String message) {
        super(new StringBuilder("Filter indicated not exist:")
                .append(message)
                .toString()
        );
    }
}
