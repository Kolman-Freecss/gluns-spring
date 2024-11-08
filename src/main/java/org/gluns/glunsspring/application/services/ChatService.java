package org.gluns.glunsspring.application.services;

import org.gluns.glunsspring.domain.model.ChatContextType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * ChatService
 * Used to define the methods that the ChatService must implement.
 */
@Service
public class ChatService {
    
    public Mono<List<ChatContextType>> getAllContexts() {
        return Mono.just(List.of(ChatContextType.values()));
    }
    
}
