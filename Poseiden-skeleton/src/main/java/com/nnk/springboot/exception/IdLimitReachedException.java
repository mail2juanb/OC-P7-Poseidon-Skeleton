package com.nnk.springboot.exception;


/**
 * Exception thrown when an ID limit has been reached.
 * Constrained between -128 and 127, stored as a TINYINT in the database.
 * This exception is used to indicate that the maximum allowable ID limit for a TinyInt
 * in the database has been exceeded.
 */
public class IdLimitReachedException extends RuntimeException {

    /**
     * Constructs a new IdLimitReachedException with the specified detail message.
     *
     * @param message The detail message explaining which ID limit was reached.
     */
    public IdLimitReachedException(String message) {
        super(message);
    }

}