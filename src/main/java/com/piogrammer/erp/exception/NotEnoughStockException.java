package com.piogrammer.erp.exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() {
        super("Not enough stock");
    }
}