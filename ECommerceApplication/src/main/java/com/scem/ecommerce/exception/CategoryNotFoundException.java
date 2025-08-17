package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
