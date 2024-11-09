package org.gluns.glunsspring.infrastructure.adapters.in.gluns.python;

import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.ports.DashboardHandlerPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
    
}
