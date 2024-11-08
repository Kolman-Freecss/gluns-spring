package org.gluns.glunsspring.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * ChatMessage class.
 * Used to define the ChatMessage object.
 */
@AllArgsConstructor
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

    @Column(nullable = false)
    private String message;

    @OneToOne(fetch = FetchType.LAZY) // For findById is not necessary to fetch the next message
    @JoinColumn(name = "previous_message_id")
    @PrimaryKeyJoinColumn
    private ChatMessage previous;

    @OneToOne(fetch = FetchType.LAZY) // For findById is not necessary to fetch the next message
    @JoinColumn(name = "next_message_id")
    @PrimaryKeyJoinColumn
    private ChatMessage next;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private ChatUserType userType;

    public ChatMessage(final long id,
                       final long chatHistoryId,
                       final String message,
                       final ChatMessage entity,
                       final ChatUserType chatUserType) {
        this.id = id;
        this.chatHistoryId = chatHistoryId;
        this.message = message;
        this.previous = clone(entity);
        this.userType = chatUserType;
    }

    public ChatMessage clone(final ChatMessage entity) {
        if (entity == null) {
            return null;
        }
        return new ChatMessage(entity.getId(),
                entity.getChatHistoryId(),
                entity.getMessage(),
                entity.getPrevious(),
                entity.getUserType());
    }

}
