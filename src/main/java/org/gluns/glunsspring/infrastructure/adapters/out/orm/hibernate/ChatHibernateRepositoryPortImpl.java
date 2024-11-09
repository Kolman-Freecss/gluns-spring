package org.gluns.glunsspring.infrastructure.adapters.out.orm.hibernate;

import lombok.AllArgsConstructor;
import org.gluns.glunsspring.application.ports.ChatRepositoryPort;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;

/**
 * ChatHibernateRepositoryPortImpl
 */
@AllArgsConstructor
@Repository("chatHibernateRepositoryPortImpl")
public class ChatHibernateRepositoryPortImpl implements ChatRepositoryPort {

    private final ChatHibernateRepository chatHibernateRepository;

    /**
     * Get all messages.
     * Find first messages from the database.
     *
     * @return Mono<List < ChatMessage>>
     */
    public Mono<List<ChatMessage>> findAll(final String userId) {
        return Mono.fromCallable(() -> this.chatHibernateRepository.findFirstMessages(userId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Save message.
     *
     * @param chatMessage
     * @return Mono<ChatMessage>
     */
    public Mono<ChatMessage> create(final ChatMessage chatMessage) {
        return Mono.fromCallable(() -> this.chatHibernateRepository.save(chatMessage))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Update message.
     *
     * @param chatMessage
     * @return Mono<ChatMessage>
     */
    public Mono<ChatMessage> update(final ChatMessage chatMessage) {
        return Mono.fromCallable(() -> this.chatHibernateRepository.save(chatMessage))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Get message by id.
     *
     * @param chatHistoryId
     * @return Mono<ChatMessage>
     */
    public Mono<Optional<ChatMessage>> findAllChatMessagesByIdHistoryId(final Long chatHistoryId,
                                                                        final String userId) {
        return Mono.fromCallable(() -> Optional.ofNullable(this.chatHibernateRepository.findAllMessagesByChatMessageId(chatHistoryId, userId)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Find last by history Id.
     *
     * @param historyId
     * @return Mono<ChatMessage>
     */
    public Mono<Optional<ChatMessage>> findLastByHistoryId(final Long historyId) {
        return Mono.fromCallable(() -> Optional.ofNullable(this.chatHibernateRepository.findLastMessageByChatHistoryId(historyId)))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Count messages by history id.
     */
    public Mono<Integer> countChatMessagesByChatHistoryId(final Long historyId) {
        return Mono.fromCallable(() -> this.chatHibernateRepository.countByChatHistoryId(historyId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * Find last by chat history id and user id.
     *
     * @param chatHistoryId
     * @param userId
     * @return Mono<ChatMessage>
     */
    public Mono<Optional<ChatMessage>> findFirstByChatHistoryIdAndUserId(final Long chatHistoryId, final String userId) {
        return Mono.fromCallable(() -> Optional.ofNullable(this.chatHibernateRepository.findFirstByChatHistoryIdAndUserId(chatHistoryId, userId)))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
