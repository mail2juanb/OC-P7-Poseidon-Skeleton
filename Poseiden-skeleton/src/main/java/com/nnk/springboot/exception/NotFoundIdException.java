package com.nnk.springboot.exception;


/**
 * Exception thrown when a requested ID is not found.
 * This exception indicates that the specified ID does not exist in the system.
 */
public class NotFoundIdException extends RuntimeException {

    /**
     * Constructs a new NotFoundIdException with the specified detail message.
     *
     * @param message The detail message explaining which ID was not found.
     */
    public NotFoundIdException(String message) {
        super(message);
    }

}
