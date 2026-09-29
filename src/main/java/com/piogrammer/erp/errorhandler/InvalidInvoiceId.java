package com.piogrammer.erp.errorhandler;

public class InvalidInvoiceId extends RuntimeException {
    public InvalidInvoiceId(String message) {
        super(message);
    }
}
