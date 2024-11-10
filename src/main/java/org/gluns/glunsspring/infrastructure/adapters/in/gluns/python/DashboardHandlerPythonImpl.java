package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python;

import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.ports.DashboardHandlerPort;
import org.gluns.glunsspring.domain.dto.DashboardGraphDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * DashboardHandlerPythonImpl class.
 * Used to define the DashboardHandlerPythonImpl object.
 */
@Slf4j
@Component
public class DashboardHandlerPythonImpl implements DashboardHandlerPort {
    
    private final WebClient webClient;
    
    public DashboardHandlerPythonImpl(
            @Qualifier("pythonWebClient") final WebClient webClient
    ) {
        this.webClient = webClient;
    }
    
    public Mono<List<DashboardGraphDto>> getDashboardGraph() {
        log.trace("Request to Python service.");
        return webClient.get()
                .uri("/api/v1/chat/summary")
                .retrieve()
                .bodyToMono(List.class)
                .mapNotNull(response -> {
                    final List<DashboardGraphDto> responseConverted = response;
                    log.trace("Response converted to List<DashboardGraphDto> size: {}", responseConverted != null ? responseConverted.size() : 0);
                    return responseConverted;
                })
                .onErrorMap(throwable -> new RuntimeException("Error getting the dashboard graph from the Python service", throwable));
    }
    
}
