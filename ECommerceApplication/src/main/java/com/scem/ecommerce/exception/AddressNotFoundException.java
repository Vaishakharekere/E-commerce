package com.scem.ecommerce.exception;

@SuppressWarnings("serial")
public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(String message) {
        super(message);
    }
}
