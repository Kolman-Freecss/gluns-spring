package org.gluns.glunsspring.application.services;

import org.gluns.glunsspring.application.mappers.ChatConverter;
import org.gluns.glunsspring.application.ports.ChatRepositoryPort;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * ChatService
 * Used to define the methods that the ChatService must implement.
 */
@Service
public class ChatService {
    
    private final ChatRepositoryPort chatRepositoryPort;
    
    private final ChatConverter chatConverter;
    
    public ChatService(@Qualifier("chatHibernateRepositoryPortImpl") final ChatRepositoryPort chatRepositoryPort,
                       final ChatConverter chatConverter) {
        this.chatRepositoryPort = chatRepositoryPort;
        this.chatConverter = chatConverter;
    }
    
    /**
     * Get all chatMessages
     */
    public Mono<List<ChatMessageDto>> findAll() {
        return this.chatRepositoryPort.findAll()
                .flatMap(chatMessages -> 
                        Mono.just(chatMessages.stream().map(chatConverter::toDto).toList()));
    }
    
    /**
     * Get chatMessage by id.
     * 
     * @param id
     * @return Mono<ChatMessage>
     */
    public Mono<ChatMessageDto> findById(final Long id) {
        return this.chatRepositoryPort.findById(id)
                .flatMap(chatMessage -> 
                        Mono.justOrEmpty(chatConverter.toDto(chatMessage.orElse(null))));
    }
    
    /**
     * Get all contexts.
     * 
     * @return Mono<List<ChatContextType>>
     */
    public Mono<List<ChatContextType>> getAllContexts() {
        return Mono.just(List.of(ChatContextType.values()));
    }
    
}
