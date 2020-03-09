package com.afu.virtualshop.exceptions;

/**
 * The type Not found exception.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
public class NotFoundException extends RuntimeException{

    /**
     * Instantiates a new Not found exception.
     *
     * @param message the message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
