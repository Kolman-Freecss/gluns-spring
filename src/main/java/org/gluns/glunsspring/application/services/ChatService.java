package org.gluns.glunsspring.application.services;

import org.gluns.glunsspring.application.mappers.ChatConverter;
import org.gluns.glunsspring.application.ports.ChatAnswerHandlerPort;
import org.gluns.glunsspring.application.ports.ChatRepositoryPort;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.gluns.glunsspring.infrastructure.adapters.in.security.JwtTokenExtractor;
import org.gluns.glunsspring.shared.exceptions.GException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

/**
 * ChatService
 * Used to define the methods that the ChatService must implement.
 */
@Service
public class ChatService {

    private final ChatRepositoryPort chatRepositoryPort;

    private final ChatConverter chatConverter;

    private final ChatAnswerHandlerPort chatAnswerHandlerPort;
    
    private final JwtTokenExtractor jwtTokenExtractor;

    public ChatService(@Qualifier("chatHibernateRepositoryPortImpl") final ChatRepositoryPort chatRepositoryPort,
                       final ChatConverter chatConverter,
                       final ChatAnswerHandlerPort chatAnswerHandlerPort,
                          final JwtTokenExtractor jwtTokenExtractor
    ) {
        this.chatRepositoryPort = chatRepositoryPort;
        this.chatConverter = chatConverter;
        this.chatAnswerHandlerPort = chatAnswerHandlerPort;
        this.jwtTokenExtractor = jwtTokenExtractor;
    }

    /**
     * Get all chatMessages
     */
    public Mono<List<ChatMessageDto>> findAll(final String authHeader) {
        return this.chatRepositoryPort.findAll(this.jwtTokenExtractor.getId(authHeader))
                .flatMap(chatMessages ->
                        Mono.just(chatMessages.stream().map(c -> {
                                    // We don't need to return the previous and next messages.
                                    c.setPrevious(null);
                                    c.setNext(null);
                                    final Integer depth = this.chatRepositoryPort.countChatMessagesByChatHistoryId(c.getChatHistoryId()).block();
                                    return chatConverter.toDto(c, depth);
                                })
                                .toList()))
                .onErrorResume(throwable -> Mono.error(new GException("Error getting all chat messages", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    /**
     * Request an answer.
     *
     * @return Mono<ChatMessageDto>
     */
    @Transactional
    public Mono<ChatMessageDto> requestAnswer(final ChatMessageDto chatMessageDto,
                                              final String authHeader
    ) {
        // Always the user is the one who requests an answer.
        ChatMessage userRequest = chatConverter.toEntity(chatMessageDto,
                this.chatRepositoryPort.countChatMessagesByChatHistoryId(chatMessageDto.chatHistoryId()).block()); // TODO: Make it async
        userRequest.setUserId(this.jwtTokenExtractor.getId(authHeader));
        userRequest.setUserType(ChatMessage.ChatUserType.USER);
        return saveChatMessage(userRequest)
                .flatMap(chatMessageSaved -> this.chatAnswerHandlerPort.getAnswer(chatMessageSaved) // Call the AI service to get the answer.
                        .flatMap(this::saveChatMessage)
                        .flatMap(chatMessage -> Mono.just(chatConverter.toDto(chatMessage, 1))) // TODO: This method is unperformant because of EAGER way -> this.chatRepositoryPort.countChatMessagesByChatHistoryId(chatMessageDto.chatHistoryId()).block()
                )
                .subscribeOn(Schedulers.boundedElastic()) // Ensures are executed on a separate thread where the transaction is open
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
     * @param chatHistoryId: Long
     * @return Mono<ChatMessage>
     */
    public Mono<ChatMessageDto> findAllChatMessagesByIdHistoryId(final Long chatHistoryId, 
                                         final String authHeader) {
        return this.chatRepositoryPort.findAllChatMessagesByIdHistoryId(chatHistoryId, this.jwtTokenExtractor.getId(authHeader))
                .flatMap(chatMessage -> {
                    if (chatMessage.isEmpty()) {
                        return Mono.empty();
                    }
                    final Integer depth = this.chatRepositoryPort.countChatMessagesByChatHistoryId(chatMessage.get().getChatHistoryId()).block();
                    return Mono.justOrEmpty(chatConverter.toDto(chatMessage.orElse(null), depth));
                })
                .onErrorResume(throwable -> Mono.error(new GException("Error getting chat message by id", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    /**
     * Get all messages by chat history id and user id.
     *
     * @param chatHistoryId: Long
     * @param userId:        String
     * @return Mono<List < ChatMessageDto>>
     */
    public Mono<ChatMessageDto> findAllMessagesByChatHistoryIdAndUserId(final Long chatHistoryId,
                                                                        final String userId
    ) {
        return this.chatRepositoryPort.findFirstByChatHistoryIdAndUserId(chatHistoryId, userId)
                .flatMap(chatMessages -> {
                            if (chatMessages.isEmpty()) {
                                return Mono.empty();
                            }
                            return Mono.just(chatMessages.map(c -> {
                                                final Integer depth = this.chatRepositoryPort.countChatMessagesByChatHistoryId(c.getChatHistoryId()).block();
                                                return chatConverter.toDto(c, depth);
                                            })
                                            .orElse(null)
                            );
                        }
                )
                .switchIfEmpty(Mono.empty())
                .onErrorResume(throwable -> Mono.error(new GException("Error getting all messages by chat history id and user id", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

    // ------------------------------------------------------------------------------------------------
    /**
     * ##########################################
     * ############## PRIVATE METHODS ###########
     * ##########################################
     */
    // ------------------------------------------------------------------------------------------------

    /**
     * Save chat message.
     *
     * @param chatMessage: ChatMessage
     * @return Mono<ChatMessage>
     */
    private Mono<ChatMessage> saveChatMessage(final ChatMessage chatMessage) {
        return this.chatRepositoryPort.findLastByHistoryId(chatMessage.getChatHistoryId())
                .flatMap(parentMessage -> {
                    // If the parent message is found, set it as the previous message.
                    if (parentMessage.isPresent()) {
                        final ChatMessage parent = parentMessage.get();

                        // Init entities (to avoid LazyInitializationException)
                        parent.getNext();
                        chatMessage.getPrevious();

                        parent.setNext(chatMessage);
                        chatMessage.setPrevious(parent);

                        return this.chatRepositoryPort.create(chatMessage)
                                .then(this.chatRepositoryPort.update(parent));
                    } else {
                        // If the parent message is not found, set the user request as the first message.
                        return this.chatRepositoryPort.create(chatMessage);
                    }
                })
                .onErrorResume(throwable -> Mono.error(new GException("Error saving chat message", throwable, HttpStatus.INTERNAL_SERVER_ERROR)));
    }

}
