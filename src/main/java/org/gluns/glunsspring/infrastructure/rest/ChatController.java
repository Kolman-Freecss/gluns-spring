package org.gluns.glunsspring.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.services.ChatService;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.infrastructure.rest.common.BaseController;
import org.gluns.glunsspring.infrastructure.rest.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * ChatController class.
 * Used to define the ChatController object.
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController extends BaseController {

    private final ChatService chatService;

    /**
     * Get history of all the chat messages.
     * Endpoint operation used by the user to get the history of all its conversations.
     *
     * @param authHeader
     */
    @Operation(summary = "Get history of all the chat messages", description = "It pulls all the first messages of all the chat histories")
    @ApiResponse(responseCode = "200", description = "History chat messages retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error retrieving message chats")
    @GetMapping("")
//    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<ResponseWrapper<List<ChatMessageDto>>>> getHistoryChatMessages(
            @RequestHeader("Authorization") String authHeader
    ) {
        return handleOperation(this.chatService.findAll(authHeader),
                HttpStatus.OK,
                "Message chats retrieved successfully");
    }

    /**
     * Request an answer.
     * Endpoint operation used by the user to request an answer when the user sends a message.
     *
     * @param authHeader
     * @param chatMessageDto
     */
    @Operation(summary = "User request an answer", description = "User request an answer")
    @ApiResponse(responseCode = "201", description = "User request an answer")
    @ApiResponse(responseCode = "500", description = "Error requesting an answer")
    @PostMapping("/request")
//    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<ResponseWrapper<ChatMessageDto>>> requestAnswer(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody final ChatMessageDto chatMessageDto
    ) {
        return handleOperation(this.chatService.requestAnswer(chatMessageDto, authHeader),
                HttpStatus.CREATED,
                "User request an answer");
    }

    /**
     * Get all messages by chat history id.
     * Endpoint operation used by the user to retrieve all messages by chat history id.
     * When user clicks on a chat history, it will retrieve all the messages of that chat history.
     * Ex endpoint: /api/v1/chat/1
     *
     * @param chatHistoryId
     */
    @Operation(summary = "Retrieve all messages by chat history id", description = "Retrieve all messages by the parent chat history id")
    @ApiResponse(responseCode = "200", description = "Chat messages found")
    @ApiResponse(responseCode = "500", description = "Error finding chat messages")
    @GetMapping("/{chatHistoryId}")
//    @JsonView(Views.Internal.class)
    public Mono<ResponseEntity<ResponseWrapper<ChatMessageDto>>> findAllChatMessagesByChatHistoryId(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable final long chatHistoryId
    ) {
        return handleOperation(this.chatService.findAllChatMessagesByIdHistoryId(chatHistoryId, authHeader),
                HttpStatus.OK,
                "Chat messages found");
    }


    @Operation(summary = "Get all contexts", description = "Get all contexts")
    @ApiResponse(responseCode = "200", description = "Contexts retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error retrieving contexts")
    @GetMapping("/contexts")
    public Mono<ResponseEntity<ResponseWrapper<List<ChatContextType>>>> getAllContexts(
    ) {
        return handleOperation(this.chatService.getAllContexts(),
                HttpStatus.OK,
                "Contexts retrieved successfully");
    }

    /**
     * Get all messages by chat history id and user id.
     * Endpoint operation used by Python service to retrieve all messages by chat history id and user id.
     *
     * @param chatHistoryId
     * @param userId
     */
    @Operation(summary = "Get all messages by chat history id and user id", description = "Get all messages by chat history id and user id")
    @ApiResponse(responseCode = "200", description = "Messages found")
    @ApiResponse(responseCode = "500", description = "Error finding messages")
    @GetMapping("/{chatHistoryId}/{userId}")
    public Mono<ResponseEntity<ResponseWrapper<ChatMessageDto>>> findAllMessagesByChatHistoryIdAndUserId(
            @PathVariable final long chatHistoryId,
            @PathVariable final String userId
    ) {
        return handleOperation(this.chatService.findAllMessagesByChatHistoryIdAndUserId(chatHistoryId, userId),
                HttpStatus.OK,
                "Messages found");
    }

}
