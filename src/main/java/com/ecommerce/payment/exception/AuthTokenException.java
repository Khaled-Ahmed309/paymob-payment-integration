package com.ecommerce.payment.exception;

public class AuthTokenException extends RuntimeException {
    public AuthTokenException(String message) {
        super(message);
    }
}
