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

    @Column(name = "chat_history_id", nullable = false)
    private long chatHistoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "context_type", nullable = false)
    private ChatContextType contextType;

    @Column(nullable = false)
    private String message;

    @Setter
    @OneToOne(fetch = FetchType.LAZY) // For findById is not necessary to fetch the next message
    @JoinColumn(name = "previous_message_id")
    private ChatMessage previous;

    @Setter
    @OneToOne(fetch = FetchType.LAZY) // For findById is not necessary to fetch the next message
    @JoinColumn(name = "next_message_id")
    private ChatMessage next;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private ChatUserType userType;

    public ChatMessage(final long id,
                       final long chatHistoryId,
                       final ChatContextType contextType,
                       final String message,
                       final ChatMessage entity,
                       final ChatMessage next,
                       final ChatUserType chatUserType) {
        this.id = id;
        this.chatHistoryId = chatHistoryId;
        this.contextType = contextType;
        this.message = message;
        this.previous = clone(entity);
        this.next = clone(next);
        this.userType = chatUserType;
    }

    public ChatMessage clone(final ChatMessage entity) {
        if (entity == null) {
            return null;
        }
        return new ChatMessage(entity.getId(),
                entity.getChatHistoryId(),
                entity.getContextType(),
                entity.getMessage(),
                entity.getPrevious(),
                entity.getNext(),
                entity.getUserType());
    }

}
