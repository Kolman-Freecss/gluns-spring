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
    
    Mono<ChatMessage> create(final ChatMessage chatMessage);
    
    Mono<ChatMessage> update(final ChatMessage chatMessage);
    
    Mono<Optional<ChatMessage>> findById(final Long id);
    
    Mono<Optional<ChatMessage>> findLastByHistoryId(final Long historyId);
    
    Mono<Integer> countChatMessagesByChatHistoryId(final Long chatHistoryId);
    
    Mono<Optional<ChatMessage>> findFirstByChatHistoryIdAndUserId(final Long chatHistoryId, final String userId);
    
}
