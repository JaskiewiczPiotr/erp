package com.piogrammer.erp.errorhandler;

public class InvalidMailCustomerException extends  InvalidCustomerDataException {
    public InvalidMailCustomerException(String message) {
        super(message);
    }
}
