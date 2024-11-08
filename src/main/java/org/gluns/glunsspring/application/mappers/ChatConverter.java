package org.gluns.glunsspring.application.mappers;

import lombok.NoArgsConstructor;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.springframework.stereotype.Component;

/**
 * ChatConverter
 */
@NoArgsConstructor
@Component
public class ChatConverter {
    
    public ChatMessageDto toDto(final ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        return new ChatMessageDto(chatMessage.getId(), 
                chatMessage.getChatHistoryId(),
                chatMessage.getMessage(), 
                toDto(chatMessage.getNext()), 
                chatMessage.getUserType());
    }
    
    public ChatMessage toEntity(final ChatMessageDto chatMessageDto) {
        if (chatMessageDto == null) {
            return null;
        }
        return new ChatMessage(chatMessageDto.id(),
                chatMessageDto.chatHistoryId(),
                chatMessageDto.message(),
                toEntity(chatMessageDto.next()),
                chatMessageDto.userType());        
    }
    
}
