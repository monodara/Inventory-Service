package com.inventorymanager.domain.exception;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
