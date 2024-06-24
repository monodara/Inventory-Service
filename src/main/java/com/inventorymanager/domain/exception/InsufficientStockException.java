package com.inventorymanager.domain.exception;

public class InsufficientStockException extends CustomException{
    public InsufficientStockException(String message) {
        super(message);
    }
}
