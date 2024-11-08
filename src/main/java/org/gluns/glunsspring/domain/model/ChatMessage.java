package org.gluns.glunsspring.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private ChatUserType userType;

    public ChatMessage(final long id,
                       long chatHistoryId,
                       String message,
                       ChatMessage entity,
                       ChatUserType chatUserType) {
        this.id = id;
        this.chatHistoryId = chatHistoryId;
        this.message = message;
        this.previous = clone(entity);
        this.userType = chatUserType;
    }

    public ChatMessage clone(ChatMessage entity) {
        return new ChatMessage(entity.getId(),
                entity.getChatHistoryId(),
                entity.getMessage(),
                entity.getPrevious(),
                entity.getUserType());
    }

}
