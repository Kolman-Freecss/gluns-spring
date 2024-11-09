package org.gluns.glunsspring.infrastructure.rest.model;

/**
 * ResponseWrapper
 * Used to define the ResponseWrapper object.
 */
public record ResponseWrapper<T>(
        String message,
        T body
) {
}

