package org.gluns.glunsspring.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ChatMessage class.
 * Used to define the ChatMessage object.
 */
@NoArgsConstructor
@Getter
@Entity
public class ChatMessage implements Cloneable {

    public enum ChatUserType {
        USER("user"),
        BOT("bot");

        private final String name;

        ChatUserType(String name) {
            this.name = name;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Setter
    @Column(name = "user_id", nullable = false)
    private String userId; // From Keycloak

    @Column(name = "chat_history_id", nullable = false)
    private long chatHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "context_type", nullable = false)
    private ChatContextType contextType;

    @Column(nullable = false)
    private String message;

    // TODO: Make it Lazy
    @Setter
    @OneToOne(fetch = FetchType.EAGER) // For findById is not necessary to fetch the next message
    @JoinColumn(name = "previous_message_id")
    private ChatMessage previous;

    // TODO: Make it Lazy
    @Setter
    @OneToOne(fetch = FetchType.EAGER) // For findById is not necessary to fetch the next message
    @JoinColumn(name = "next_message_id")
    private ChatMessage next;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private ChatUserType userType;

    public ChatMessage(final long id,
                          final String userId,
                       final long chatHistoryId,
                       final ChatContextType contextType,
                       final String message,
                       final ChatMessage previous,
                       final ChatMessage next,
                       final ChatUserType chatUserType) {
        this.id = id;
        this.userId = userId;
        this.chatHistoryId = chatHistoryId;
        this.contextType = contextType;
        this.message = message;
        this.previous = clone(previous, 1);
        this.next = clone(next, 1);
        this.userType = chatUserType;
    }

    public ChatMessage clone(final ChatMessage entity, final int depth) {
        if (entity == null || depth <= -1) {
            return null;
        }
        return new ChatMessage(entity.getId(),
                entity.getUserId(),
                entity.getChatHistoryId(),
                entity.getContextType(),
                entity.getMessage(),
                clone(entity.getPrevious(), depth - 1),
                clone(entity.getNext(), depth - 1),
                entity.getUserType());
    }

}
