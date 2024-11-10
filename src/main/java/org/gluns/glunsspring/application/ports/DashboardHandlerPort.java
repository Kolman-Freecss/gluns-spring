package org.gluns.glunsspring.application.ports;

import org.gluns.glunsspring.domain.dto.DashboardGraphDto;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * DashboardHandlerPort
 */
public interface DashboardHandlerPort {
    
    Mono<List<DashboardGraphDto>> getDashboardGraph();
    
}
