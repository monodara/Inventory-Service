package com.inventorymanager.infrastructure.middleware;

import com.inventorymanager.controller.shared.ErrorEntity;
import com.inventorymanager.controller.shared.ErrorResponseEntity;
import com.inventorymanager.domain.exception.BadRequestException;
import com.inventorymanager.domain.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNotFoundException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleBadRequestException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllException(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleValidationException(MethodArgumentNotValidException ex){
        List<ErrorEntity> errors = new ArrayList<>();
        for(FieldError fieldError: ex.getBindingResult().getFieldErrors()){
            /*ErrorEntity errorEntity = ErrorEntity
                        .builder()
                        .field(fieldError.getField()).message(fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .build();*/
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
