package com.ecommerce.payment.exception;

public class PayloadMissingException extends RuntimeException {
    public PayloadMissingException(String message) {
        super(message);
    }
}
