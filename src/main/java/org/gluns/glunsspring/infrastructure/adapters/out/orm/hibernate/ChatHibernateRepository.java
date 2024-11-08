package org.gluns.glunsspring.infrastructure.adapters.out.orm.hibernate;

import org.gluns.glunsspring.domain.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ChatHibernateRepository class.
 * Used to define the ChatHibernateRepository object.
 */
@Repository
public interface ChatHibernateRepository extends JpaRepository<ChatMessage, Long> {
   
    // JPQL query to get all first messages.
    @Query("SELECT c FROM ChatMessage c WHERE c.previous IS NULL")
    List<ChatMessage> findFirstMessages();
    
    // JPQL query to find the last message sent of a chatHistory.
    @Query("SELECT c FROM ChatMessage c WHERE c.chatHistoryId = :chatHistoryId AND c.next IS NULL")
    ChatMessage findLastMessageByChatHistoryId(final long chatHistoryId);
    
    // JPQL query to retrieve all chat messages by chatMessageId with eager over next messages.
    @Query("SELECT c FROM ChatMessage c LEFT JOIN FETCH c.next WHERE c.id = :chatMessageId")
    ChatMessage findAllMessagesByChatMessageId(final long chatMessageId);
    
}
