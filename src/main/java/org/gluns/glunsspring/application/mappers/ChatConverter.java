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
    
    public ChatMessageDto toDto(final ChatMessage chatMessage, final int depth) {
        if (chatMessage == null || depth <= -1) {
            return null;
        }
        return new ChatMessageDto(chatMessage.getId(),
                chatMessage.getUserId(),
                chatMessage.getChatHistoryId(),
                chatMessage.getContextType(),
                chatMessage.getMessage(), 
                toDto(chatMessage.getPrevious(), depth - 1),
                toDto(chatMessage.getNext(), depth - 1), 
                chatMessage.getUserType());
    }
    
    public ChatMessage toEntity(final ChatMessageDto chatMessageDto, final int depth) {
        if (chatMessageDto == null || depth <= -1) {
            return null;
        }
        return new ChatMessage(chatMessageDto.id(),
                chatMessageDto.userId(),
                chatMessageDto.chatHistoryId(),
                chatMessageDto.contextType(),
                chatMessageDto.message(),
                toEntity(chatMessageDto.previous(), depth - 1),
                toEntity(chatMessageDto.next(), depth - 1),
                chatMessageDto.userType());        
    }
    
}
