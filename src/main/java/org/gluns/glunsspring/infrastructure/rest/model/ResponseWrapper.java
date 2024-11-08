package org.gluns.glunsspring.infrastructure.rest.model;

import org.springframework.http.HttpStatus;

/**
 * ResponseWrapper
 * Used to define the ResponseWrapper object.
 */
public record ResponseWrapper<T>(
        String message,
        T body
) {
}

