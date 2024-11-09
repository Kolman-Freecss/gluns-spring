package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.mappers;

import org.gluns.glunsspring.domain.model.ChatMessage;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.ChatAnswerPythonResponse;
import org.springframework.stereotype.Component;

/**
 * ChatAnswerPythonConverter
 * @see org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.ChatAnswerHandlerPythonImpl
 */
@Component
public class ChatAnswerPythonConverter {
    
    /**
     * Convert the ChatAnswerPythonResponse to ChatMessage
     * Always the ChatUserType will be BOT
     * @param original ChatMessage
     * @param chatAnswerPythonResponse ChatAnswerPythonResponse
     * @return ChatMessage
     * @see ChatMessage
     */
    public ChatMessage toChatMessage(final ChatMessage original, final ChatAnswerPythonResponse chatAnswerPythonResponse) {
        return new ChatMessage(
                original.getId(),
                original.getChatHistoryId(),
                original.getContextType(),
                chatAnswerPythonResponse.output(),
                original.getPrevious(),
                original.getNext(),
                ChatMessage.ChatUserType.BOT
        );
    }
    
}
