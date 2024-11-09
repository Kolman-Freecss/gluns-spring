package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python;

import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.ports.ChatAnswerHandlerPort;
import org.gluns.glunsspring.domain.model.ChatMessage;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.mappers.ChatAnswerPythonConverter;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.ChatAnswerPythonRequest;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.ChatAnswerPythonResponse;
import org.gluns.glunsspring.shared.exceptions.GException;
import org.gluns.glunsspring.shared.utils.DebugApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * ChatAnswerHandlerPythonImpl
 * This service will communicate agains the Python service where is AI LLM alocated.
 */
@Slf4j
@Component
public class ChatAnswerHandlerPythonImpl implements ChatAnswerHandlerPort {

    private final WebClient webClient;
    
    private final ChatAnswerPythonConverter converter;
    
    public ChatAnswerHandlerPythonImpl(@Qualifier("pythonWebClient") final WebClient webClient,
                                       final ChatAnswerPythonConverter converter) {
        this.webClient = webClient;
        this.converter = converter;
    }
    
    @Override
    public Mono<ChatMessage> getAnswer(final ChatMessage chatMessage) {
        final ChatAnswerPythonRequest chatAnswerPythonRequest = new ChatAnswerPythonRequest(
                chatMessage.getChatHistoryId(),
                chatMessage.getUserId(),
                chatMessage.getContextType(),
                chatMessage.getMessage()
        );
        log.info("Request to Python service. Data: {}", chatAnswerPythonRequest);
        return DebugApi.getMockAnswer()
                .map(response -> {
                    final ChatMessage responseConverted = converter.toChatMessage(chatMessage, response);
                    log.trace("Response converted to ChatMessageDto: {}", responseConverted);
                    return responseConverted;
                })
                .onErrorMap(throwable -> new GException("Error getting the answer from the Python service", throwable)); // Transforms any error into a GException
//        return webClient.post()
//                .uri("/output")
//                .bodyValue(chatAnswerPythonRequest)
//                .retrieve()
//                .bodyToMono(ChatAnswerPythonResponse.class) // We receive the response from the Python service
//                .map(response -> {
//                    final ChatMessage responseConverted = converter.toChatMessage(chatMessage, response);
//                    log.trace("Response converted to ChatMessageDto: {}", responseConverted);
//                    return responseConverted;
//                })
//                .onErrorMap(throwable -> new GException("Error getting the answer from the Python service", throwable)); // Transforms any error into a GException
    }
}
