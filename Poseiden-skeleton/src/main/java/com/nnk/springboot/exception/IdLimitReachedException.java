package com.nnk.springboot.exception;

public class IdLimitReachedException extends RuntimeException {

    public IdLimitReachedException(String message) {
        super(message);
    }

}