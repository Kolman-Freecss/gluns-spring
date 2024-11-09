package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python;

import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.ports.TipsHandlerPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
    
}
