package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
