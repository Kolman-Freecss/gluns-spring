package org.gluns.glunsspring.application.ports;

import org.gluns.glunsspring.domain.model.ChatMessage;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

/**
 * ChatRepositoryPort
 */
public interface ChatRepositoryPort {
    
    Mono<List<ChatMessage>> findAll();
    
    Mono<Optional<ChatMessage>> findById(final Long id);
    
}
