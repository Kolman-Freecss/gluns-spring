package org.gluns.glunsspring.infrastructure.rest;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.services.ChatService;
import org.gluns.glunsspring.domain.dto.ChatMessageDto;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.infrastructure.rest.common.BaseController;
import org.gluns.glunsspring.infrastructure.rest.model.ResponseWrapper;
import org.gluns.glunsspring.infrastructure.rest.model.Views;
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
    
    @Operation(summary = "Get history of all the chat messages", description = "It pulls all the first messages of all the chat histories")
    @ApiResponse(responseCode = "200", description = "History chat messages retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error retrieving message chats")
    @GetMapping("/")
//    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<ResponseWrapper<List<ChatMessageDto>>>> getHistoryChatMessages() {
        return handleOperation(this.chatService.findAll(),
                HttpStatus.OK,
                "Message chats retrieved successfully");
    }
    
    @Operation(summary = "User request an answer", description = "User request an answer")
    @ApiResponse(responseCode = "201", description = "User request an answer")
    @ApiResponse(responseCode = "500", description = "Error requesting an answer")
    @PostMapping("/request")
//    @JsonView(Views.Public.class)
    public Mono<ResponseEntity<ResponseWrapper<ChatMessageDto>>> requestAnswer(@RequestBody final ChatMessageDto chatMessageDto) {
        return handleOperation(this.chatService.requestAnswer(chatMessageDto),
                HttpStatus.CREATED,
                "User request an answer");
    }
    
    @Operation(summary = "Retrieve all messages by chat history id", description = "Retrieve all messages by the parent chat history id")
    @ApiResponse(responseCode = "200", description = "Chat messages found")
    @ApiResponse(responseCode = "500", description = "Error finding chat messages")
    @GetMapping("/{chatMessageId}")
//    @JsonView(Views.Internal.class)
    public Mono<ResponseEntity<ResponseWrapper<ChatMessageDto>>> findAllChatMessagesByChatHistoryId(@PathVariable final long chatMessageId) {
        return handleOperation(this.chatService.findById(chatMessageId),
                HttpStatus.OK,
                "Chat messages found");
    }
    

    @Operation(summary = "Get all contexts", description = "Get all contexts")
    @ApiResponse(responseCode = "200", description = "Contexts retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error retrieving contexts")
    @GetMapping("/contexts")
    public Mono<ResponseEntity<ResponseWrapper<List<ChatContextType>>>> getAllContexts() {
        return handleOperation(this.chatService.getAllContexts(),
                HttpStatus.OK,
                "Contexts retrieved successfully");
    }

}
