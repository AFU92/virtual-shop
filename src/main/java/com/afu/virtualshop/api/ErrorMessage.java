package com.afu.virtualshop.api;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * The type Error message.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@Data

public class ErrorMessage {

    private String message;

    private List<String> errors;

    /**
     * Instantiates a new Error message.
     *
     * @param message the message
     * @param errors  the errors
     */
    public ErrorMessage(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }

    /**
     * Instantiates a new Error message.
     *
     * @param message the message
     */
    public ErrorMessage(String message) {
        this.message = message;
        this.errors = new ArrayList<String>();
    }
}
