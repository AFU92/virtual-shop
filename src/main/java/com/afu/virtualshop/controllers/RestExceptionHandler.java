package com.afu.virtualshop.controllers;

import com.afu.virtualshop.exceptions.ProviderException;
import com.afu.virtualshop.models.api.ErrorMessage;
import com.afu.virtualshop.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Rest exception handler.
 *
 * @author Andrea Fuentes (andrea.fuentes@payulatam.com)
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String BAD_REQUEST_MESSAGE = "The request message is invalid";

    /**
     * Handle constraint violation response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<ErrorMessage> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        ex.getConstraintViolations().forEach((ConstraintViolation<?> violation) -> errors
                .add(violation.getPropertyPath() + ": " + violation.getMessage()));
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(BAD_REQUEST_MESSAGE, errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle data integrity violation response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolation(DataIntegrityViolationException ex, WebRequest request) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(BAD_REQUEST_MESSAGE,
                Arrays.asList(ex.getCause().getCause().getMessage())),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle illegal argument response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ IllegalArgumentException.class})
    public ResponseEntity<ErrorMessage> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(BAD_REQUEST_MESSAGE,
                Arrays.asList(ex.getMessage())),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ ProviderException.class})
    public ResponseEntity<ErrorMessage> handleProviderException(ProviderException ex, WebRequest request) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(BAD_REQUEST_MESSAGE,
                Arrays.asList(ex.getMessage())),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle not found response entity.
     *
     * @param ex      the ex
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({ NotFoundException.class })
    public ResponseEntity<ErrorMessage> handleNotFound(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<ErrorMessage>(new ErrorMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

}
