package org.gluns.glunsspring.shared.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.ChatAnswerPythonResponse;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * DebugApi
 * @version 1.0
 */
public class DebugApi {

    /**
     * Read answer.json from mock folder and return the response
     * Ex:
     *      DebugApi.getMockAnswer()
                .map(response -> {
                    final ChatMessage responseConverted = converter.toChatMessage(chatMessage, response);
                    log.trace("Response converted to ChatMessageDto: {}", responseConverted);
                    return responseConverted;
                })
                .onErrorMap(throwable -> new GException("Error getting the answer from the Python service", throwable)); // Transforms any error into a GException
     * 
     * @return Mono<ChatAnswerPythonResponse>
     */
    public static Mono<ChatAnswerPythonResponse> getMockAnswer() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            final ChatAnswerPythonResponse chatAnswerPythonResponse = objectMapper.readValue(
                    DebugApi.class.getClassLoader().getResourceAsStream(".data/.mocks/answer.json"),
                    ChatAnswerPythonResponse.class
            );
            return Mono.just(chatAnswerPythonResponse);
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

}
