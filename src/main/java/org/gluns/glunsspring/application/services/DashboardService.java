package org.gluns.glunsspring.application.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.ports.DashboardHandlerPort;
import org.gluns.glunsspring.domain.dto.DashboardGraphDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * DashboardService class.
 * Used to define the DashboardService object.
 */
@Slf4j
@AllArgsConstructor
@Service
public class DashboardService {

    private final DashboardHandlerPort dashboardHandlerPort;

    /**
     * getDashboardGraph
     * Used to get the dashboard graph.
     *
     * @return Mono<DashboardGraphDto>
     */
    public Mono<List<DashboardGraphDto>> getDashboardGraph(
            final String authHeader
    ) {
        log.trace("Getting the dashboard graph");
        return this.dashboardHandlerPort.getDashboardGraph();
    }

}
