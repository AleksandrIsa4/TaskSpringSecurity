package org.example.springsecurityfirsthw.exceptions;

public class DataNotFoundException extends RuntimeException {
    public DataNotFoundException(String message) {
        super(message);
    }
}
