package com.dbs.micronaut.demo.exception;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;

import javax.inject.Singleton;

/**
 * This class is registered with Micronaut and handles any BusinessException that is thrown by any Controller.
 */
@Produces
@Singleton
@Requires(classes = {BusinessException.class, ExceptionHandler.class})
public class BusinessExceptionHandler implements ExceptionHandler<BusinessException, HttpResponse> {

    // ------------------------------------------------ PUBLIC METHODS -------------------------------------------------

    /**
     * Converts an BusinessException into an HTTP 400 - BAD REQUEST and returns the exception details as JSON.
     */
    @Override
    public HttpResponse handle(HttpRequest request, BusinessException exception) {
        return HttpResponse.badRequest(exception);
    }

    // -----------------------------------------------------------------------------------------------------------------
}
