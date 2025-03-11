package com.nnk.springboot.exception;

public class NotFoundIdException extends RuntimeException {

    public NotFoundIdException(String message) {
        super(message);
    }

}
