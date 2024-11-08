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
    
}
