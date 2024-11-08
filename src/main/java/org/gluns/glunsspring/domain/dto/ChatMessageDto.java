package org.gluns.glunsspring.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.domain.model.ChatMessage;

import java.util.Optional;

/**
 * ChatMessageDto class.
 * @param id
 * @param next
 * @param userType
 */
public record ChatMessageDto(
        @Schema(hidden = true) long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) long chatHistoryId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) ChatContextType contextType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String message,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) ChatMessageDto previous,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) ChatMessageDto next,
        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED) ChatMessage.ChatUserType userType
) {
    
    @JsonIgnore
    public Optional<ChatMessageDto> getPrevious() {
        return Optional.ofNullable(previous);
    }
    
    @JsonIgnore
    public Optional<ChatMessageDto> getNext() {
        return Optional.ofNullable(next);
    }
    
    @JsonIgnore
    public Optional<ChatMessage.ChatUserType> getUserType() {
        return Optional.ofNullable(userType);
    }
}
