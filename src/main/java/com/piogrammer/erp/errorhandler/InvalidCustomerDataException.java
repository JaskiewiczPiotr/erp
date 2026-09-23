package com.piogrammer.erp.errorhandler;

public class InvalidCustomerDataException extends RuntimeException {
    public InvalidCustomerDataException(String message) {
        super(message);
    }
}
