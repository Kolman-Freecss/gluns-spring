package org.gluns.glunsspring.infrastructure.rest.common;

import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.infrastructure.rest.model.ResponseWrapper;
import org.gluns.glunsspring.shared.exceptions.GException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 * BaseController class.
 * Used to define the BaseController object.
 */
@Slf4j
public abstract class BaseController {

    protected <T> Mono<ResponseEntity<ResponseWrapper<T>>> handleOperation(final Mono<T> monoData,
                                                                           final HttpStatus status,
                                                                           final String successMessage) {
        return monoData
                .map(d -> ResponseEntity.status(status).body(new ResponseWrapper<>(successMessage, d)))
                .onErrorResume(GException.class, e -> {
                    log.error(e.getMessage(), e);
                    return Mono.just(ResponseEntity.status(e.getHttpStatus()).body(new ResponseWrapper<>(e.getApiMessage(), null)));
                })
                .onErrorResume(Exception.class, e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseWrapper<>("Server error", null))));
    }

}
