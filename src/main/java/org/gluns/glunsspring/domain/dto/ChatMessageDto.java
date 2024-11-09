package org.gluns.glunsspring.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.gluns.glunsspring.infrastructure.rest.model.Views;

import java.util.Optional;

/**
 * ChatMessageDto class.
 *
 * @param id
 * @param next
 * @param userType
 */
public record ChatMessageDto(
        @Schema(hidden = true)
//        @JsonView(Views.Public.class)
        long id,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
//        @JsonView(Views.Public.class)
        long chatHistoryId,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
//        @JsonView(Views.Public.class)
        ChatContextType contextType,
        @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
//        @JsonView(Views.Public.class)
        String message,
        @Schema(hidden = true)
//        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
//        @JsonView(Views.Internal.class)
        ChatMessageDto previous,
        @Schema(hidden = true)
//        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
//        @JsonView(Views.Internal.class)
        ChatMessageDto next,
        @Schema(hidden = true)
//        @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
//        @JsonView(Views.Internal.class)
        ChatMessage.ChatUserType userType
) {

//    @JsonIgnore
//    public Optional<ChatMessageDto> getPrevious() {
//        return Optional.ofNullable(previous);
//    }
//
//    @JsonIgnore
//    public Optional<ChatMessageDto> getNext() {
//        return Optional.ofNullable(next);
//    }
//
//    @JsonIgnore
//    public Optional<ChatMessage.ChatUserType> getUserType() {
//        return Optional.ofNullable(userType);
//    }
}