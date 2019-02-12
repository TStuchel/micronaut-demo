package com.dbs.micronaut.demo.exception;

/**
 * This exception type is used to distinguish business-layer exceptions from other types of exceptions. The importance
 * is that an application that throws this exception will result in an HTTP 400 - BAD REQUEST status code being thrown
 * to a web service caller.
 */
public class BusinessException extends Exception {

    // ------------------------------------------------- CONSTRUCTORS --------------------------------------------------

    public BusinessException(String message) {
        super(message);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
