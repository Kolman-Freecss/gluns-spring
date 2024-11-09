package org.gluns.glunsspring.infrastructure.adapters.out.orm.hibernate;

import org.gluns.glunsspring.domain.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Query("SELECT c FROM ChatMessage c LEFT JOIN FETCH c.previous LEFT JOIN FETCH c.next WHERE c.chatHistoryId = :chatHistoryId AND c.next IS NULL")
    ChatMessage findLastMessageByChatHistoryId(final long chatHistoryId);

    // JPQL query to retrieve all chat messages by chatMessageId with eager over next messages.
    @Query("SELECT c FROM ChatMessage c " +
            "LEFT JOIN FETCH c.previous p " +
            "LEFT JOIN FETCH c.next n " +
            "LEFT JOIN FETCH p.previous pp " +
            "LEFT JOIN FETCH n.next nn " +
            "WHERE c.id = :chatMessageId")
    ChatMessage findAllMessagesByChatMessageId(@Param("chatMessageId") long chatMessageId);

    // Query to count the number of messages by chatHistoryId.
    Integer countByChatHistoryId(final long chatHistoryId);
    
    // JPQL query to find the last message sent by a user in a chatHistory.
    @Query("SELECT c FROM ChatMessage c WHERE c.chatHistoryId = :chatHistoryId AND c.userId = :userId AND c.previous IS NULL")    
    ChatMessage findFirstByChatHistoryIdAndUserId(final long chatHistoryId, final String userId);

}
