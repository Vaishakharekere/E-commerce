package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
