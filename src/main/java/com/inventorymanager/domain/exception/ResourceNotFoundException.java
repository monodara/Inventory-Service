package com.inventorymanager.domain.exception;

public class ResourceNotFoundException extends CustomException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
