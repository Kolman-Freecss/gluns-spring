package org.gluns.glunsspring.application.services;

import org.gluns.glunsspring.application.mappers.ChatConverter;
import org.gluns.glunsspring.application.ports.ChatRepositoryPort;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.gluns.glunsspring.shared.exceptions.GException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                        Mono.just(chatMessages.stream().map(chatConverter::toDto).toList()))
                .onErrorResume(throwable -> Mono.error(new GException("Error getting all chat messages", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    /**
     * Request an answer.
     *
     * @return Mono<ChatMessageDto>
     */
    @Transactional
    public Mono<ChatMessageDto> requestAnswer(final ChatMessageDto chatMessageDto) {
        // TODO: Call the AI service to get the answer.
        // Always the user is the one who requests an answer.
        ChatMessage userRequest = chatConverter.toEntity(chatMessageDto);
        userRequest.setUserType(ChatMessage.ChatUserType.USER);
        return this.chatRepositoryPort.findLastByHistoryId(chatMessageDto.chatHistoryId())
            .flatMap(parentMessage -> {
                // If the parent message is found, set it as the previous message.
                if (parentMessage.isPresent()) {
                    ChatMessage parent = parentMessage.get();
                    
                    parent.setNext(userRequest);
                    userRequest.setPrevious(parent);
                    // Init entities 
                    parent.getNext();
                    userRequest.getPrevious();
                    
                    return this.chatRepositoryPort.create(userRequest)
                            .then(this.chatRepositoryPort.update(parent));
                } else {
                    // If the parent message is not found, set the user request as the first message.
                    return this.chatRepositoryPort.create(userRequest);
                }
            })
            .flatMap(chatMessage -> Mono.just(chatConverter.toDto(chatMessage)))
            .onErrorResume(throwable -> {
                if (throwable instanceof GException) {
                    return Mono.error(throwable);
                }
                return Mono.error(new GException("Error requesting an answer", throwable, HttpStatus.INTERNAL_SERVER_ERROR));
            });
    }

    /**
     * Get all contexts.
     *
     * @return Mono<List < ChatContextType>>
     */
    public Mono<List<ChatContextType>> getAllContexts() {
        return Mono.just(List.of(ChatContextType.values()))
                .onErrorResume(throwable -> Mono.error(new GException("Error getting all contexts", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }
    
    /**
     * Get chatMessage by id.
     * Retrieves a chat message by its id and pulls all the next messages of the same chat history.
     * 
     * @param id: Long
     * @return Mono<ChatMessage>
     */
    public Mono<ChatMessageDto> findById(final Long id) {
        return this.chatRepositoryPort.findById(id)
                .flatMap(chatMessage ->
                        Mono.justOrEmpty(chatConverter.toDto(chatMessage.orElse(null))))
                .onErrorResume(throwable -> Mono.error(new GException("Error getting chat message by id", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

}
