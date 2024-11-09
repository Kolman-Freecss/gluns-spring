package org.gluns.glunsspring.application.ports;

import org.gluns.glunsspring.domain.model.ChatMessage;
import reactor.core.publisher.Mono;

/**
 * ChatAnswerHandlerPort
 */
public interface ChatAnswerHandlerPort {
    
    Mono<ChatMessage> getAnswer(final ChatMessage chatMessage); 
    
}
