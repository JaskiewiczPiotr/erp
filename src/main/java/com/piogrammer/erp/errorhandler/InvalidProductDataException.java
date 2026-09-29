package com.piogrammer.erp.errorhandler;

public class InvalidProductDataException extends RuntimeException {
    public InvalidProductDataException(String message) {
        super(message);
    }
}
