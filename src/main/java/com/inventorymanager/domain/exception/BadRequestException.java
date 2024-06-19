package com.inventorymanager.domain.exception;

public class BadRequestException extends CustomException{
    public BadRequestException(String message) {
        super(message);
    }
}
