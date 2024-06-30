package com.inventorymanager.infrastructure.middleware;

import com.inventorymanager.controller.shared.ErrorEntity;
import com.inventorymanager.controller.shared.ErrorResponseEntity;
import com.inventorymanager.domain.exception.BadRequestException;
import com.inventorymanager.domain.exception.InsufficientStockException;
import com.inventorymanager.domain.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseEntity> handleNotFoundException(Exception ex){
        logger.error("ResourceNotFoundException: {}", ex.getMessage());
        ErrorEntity errorEntity = ErrorEntity.builder()
                .field("resource")
                .message(ex.getMessage())
                .build();
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.builder()
                .errors(List.of(errorEntity))
                .build();
        return new ResponseEntity<>(errorResponseEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleBadRequestException(Exception ex){
        logger.error("BadRequestException: {}", ex.getMessage());
        ErrorEntity errorEntity = ErrorEntity.builder()
                .field("resource")
                .message(ex.getMessage())
                .build();
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.builder()
                .errors(List.of(errorEntity))
                .build();
        return new ResponseEntity<>(errorResponseEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleInsufficientStockException(Exception ex){
        logger.error("InsufficientStockException: {}", ex.getMessage());
        ErrorEntity errorEntity = ErrorEntity.builder()
                .field("resource")
                .message(ex.getMessage())
                .build();
        ErrorResponseEntity errorResponseEntity = ErrorResponseEntity.builder()
                .errors(List.of(errorEntity))
                .build();
        return new ResponseEntity<>(errorResponseEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseEntity> handleAllException(Exception ex){
        logger.error("Exception: {}", ex.getMessage());
        List<ErrorEntity> errors = new ArrayList<>();

        if (ex instanceof BindException) {
            for (FieldError fieldError : ((BindException) ex).getBindingResult().getFieldErrors()) {
                ErrorEntity errorEntity = new ErrorEntity();
                errorEntity.setField(fieldError.getField());
                errorEntity.setMessage(fieldError.getField() + " " + fieldError.getDefaultMessage());
                errors.add(errorEntity);
            }
        } else {
            ErrorEntity errorEntity = new ErrorEntity();
            errorEntity.setField("Exception");
            errorEntity.setMessage(ex.getMessage());
            errors.add(errorEntity);
        }

        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity();
        errorResponseEntity.setErrors(errors);

        return new ResponseEntity<>(errorResponseEntity, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleValidationException(MethodArgumentNotValidException ex){
        List<ErrorEntity> errors = new ArrayList<>();
        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            ErrorEntity errorEntity = new ErrorEntity();
            errorEntity.setField(fieldError.getField());
            errorEntity.setMessage(fieldError.getField() + " " + fieldError.getDefaultMessage());
            errors.add(errorEntity);
        }

        ErrorResponseEntity errorResponseEntity = new ErrorResponseEntity();
        errorResponseEntity.setErrors(errors);
        return new ResponseEntity<>(errorResponseEntity, HttpStatus.BAD_REQUEST);
    }
}
