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
    public Mono<List<ChatMessage>> findAll() {
        return Mono.fromCallable(this.chatHibernateRepository::findFirstMessages)
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
     * @param id
     * @return Mono<ChatMessage>
     */
    public Mono<Optional<ChatMessage>> findById(final Long id) {
        return Mono.fromCallable(() -> Optional.ofNullable(this.chatHibernateRepository.findAllMessagesByChatMessageId(id)))
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
}
