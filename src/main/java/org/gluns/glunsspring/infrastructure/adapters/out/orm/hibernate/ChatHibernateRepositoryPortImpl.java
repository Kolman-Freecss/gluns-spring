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
     * @return Mono<List<ChatMessage>>
     */
    public Mono<List<ChatMessage>> findAll() {
        return Mono.fromCallable(this.chatHibernateRepository::findFirstMessages)
                .subscribeOn(Schedulers.boundedElastic());
    }
    
    /**
     * Get message by id.
     * 
     * @param id
     * @return Mono<ChatMessage>
     */
    public Mono<Optional<ChatMessage>> findById(final Long id) {
        return Mono.fromCallable(() -> this.chatHibernateRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
