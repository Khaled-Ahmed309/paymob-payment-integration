package com.ecommerce.payment.exception;

public class TokenPaymentException extends RuntimeException {
    public TokenPaymentException(String message) {
        super(message);
    }
}
