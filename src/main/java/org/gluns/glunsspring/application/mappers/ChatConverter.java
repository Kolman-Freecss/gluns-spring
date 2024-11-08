package org.gluns.glunsspring.application.mappers;

import lombok.NoArgsConstructor;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * ChatConverter
 */
@NoArgsConstructor
@Component
public class ChatConverter {
    
    @Transactional
    public ChatMessageDto toDto(final ChatMessage chatMessage) {
        if (chatMessage == null) {
            return null;
        }
        return new ChatMessageDto(chatMessage.getId(), 
                chatMessage.getChatHistoryId(),
                chatMessage.getContextType(),
                chatMessage.getMessage(), 
                toDto(chatMessage.getNext()), 
                toDto(chatMessage.getPrevious()),
                chatMessage.getUserType());
    }
    
    @Transactional
    public ChatMessage toEntity(final ChatMessageDto chatMessageDto) {
        if (chatMessageDto == null) {
            return null;
        }
        return new ChatMessage(chatMessageDto.id(),
                chatMessageDto.chatHistoryId(),
                chatMessageDto.contextType(),
                chatMessageDto.message(),
                toEntity(chatMessageDto.previous()),
                toEntity(chatMessageDto.next()),
                chatMessageDto.userType());        
    }
    
}
