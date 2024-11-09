package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python;

import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.ports.TipsHandlerPort;
import org.gluns.glunsspring.domain.dto.TipsAnswerDto;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.TipsAnswerPythonRequest;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.TipsAnswerPythonResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * TipsHandlerPythonImpl class.
 * Used to define the TipsHandlerPythonImpl object.
 */
@Slf4j
@Component
public class TipsHandlerPythonImpl implements TipsHandlerPort {
    
    private final WebClient webClient;
    
    public TipsHandlerPythonImpl(
            @Qualifier("pythonWebClient") final WebClient webClient
    ) {
        this.webClient = webClient;
    }
    
    
    public Mono<TipsAnswerDto> getAnswer(
            final TipsAnswerPythonRequest tipsAnswerPythonRequest
    ) {
        log.trace("Request to Python service. Data: {}", tipsAnswerPythonRequest);
        return webClient.post()
                .uri("/api/v1/output")
                .bodyValue(tipsAnswerPythonRequest)
                .retrieve()
                .bodyToMono(TipsAnswerPythonResponse.class)
                .map(response -> {
                    final TipsAnswerDto responseConverted = new TipsAnswerDto(
                            tipsAnswerPythonRequest.input(), // IMPORTANT! This will be the title
                            response.output()
                    );
                    log.trace("Response converted to TipsAnswerDto: {}", responseConverted);
                    return responseConverted;
                })
                .onErrorMap(throwable -> new RuntimeException("Error getting the answer from the Python service", throwable));
    }
    
}
