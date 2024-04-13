package com.example.exceptionHandlers;

import com.example.dtos.ExceptionDto;
import com.example.dtos.ProductNotFoundDto;
import com.example.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandlers {

    // ControllerAdvice is used to handle the exceptions globally across the application
    // Exception Handlers are used to handle the exceptions thrown by the application and return the response with appropriate status code and message
    // Controllers should not handle the exceptions, they should throw the exceptions and ExceptionHandlers should handle them


    // Exception Handler for ArithmeticException
    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<Void> handleArithmeticException() {
        // If ArithmeticException is thrown then return BAD_REQUEST status code
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Exception Handler for NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDto> handleNullPointerException(NullPointerException ex) {
        // If NullPointerException is thrown then return NOT_IMPLEMENTED status code along with message from exception and resolution
        // We want to return a JSON response with message and resolution therefore we are using ExceptionDto
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setResolution("Please check the code for null values");
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_IMPLEMENTED);
    }


    // Exception Handler for ProductNotFoundException, takes ProductNotFoundException as parameter
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundDto> handleProductNotFoundException(ProductNotFoundException ex) {
        // If ProductNotFoundException is thrown then return NOT_FOUND status code along with message from exception
        ProductNotFoundDto productNotFoundDto = new ProductNotFoundDto();
        productNotFoundDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(productNotFoundDto, HttpStatus.NOT_FOUND);
    }
}
