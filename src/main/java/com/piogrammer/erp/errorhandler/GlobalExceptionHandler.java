package com.piogrammer.erp.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, String> createError(String message){
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return error;
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBusinessError(IllegalStateException ex) {

        return createError(ex.getMessage());
    }
    @ExceptionHandler(CustomerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleCustomerNotFound(CustomerNotFoundException ex) {

        return createError(ex.getMessage());
    }

    @ExceptionHandler(InvalidCustomerDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidCustomer(InvalidCustomerDataException ex) {

        return createError(ex.getMessage());
    }

    @ExceptionHandler(InvalidInvoiceId.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleInvalidInvoiceId(InvalidInvoiceId ex) {

        return createError(ex.getMessage());
    }
    @ExceptionHandler(InvalidProductDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidProduct(InvalidProductDataException ex) {
        return createError(ex.getMessage());
    }

}