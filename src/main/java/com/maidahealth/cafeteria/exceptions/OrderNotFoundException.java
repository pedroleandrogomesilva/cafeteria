package com.maidahealth.cafeteria.exceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
