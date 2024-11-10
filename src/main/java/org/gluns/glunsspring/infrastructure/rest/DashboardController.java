package org.gluns.glunsspring.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.services.DashboardService;
import org.gluns.glunsspring.domain.dto.DashboardGraphDto;
import org.gluns.glunsspring.infrastructure.rest.common.BaseController;
import org.gluns.glunsspring.infrastructure.rest.model.ResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * DashboardController class.
 * Used to define the DashboardController object.
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController extends BaseController {

    private final DashboardService dashboardService;

    /**
     * Get dashboard graph.
     * Endpoint operation used by the user to get the dashboard graph.
     *
     * @param authHeader
     */
    @Operation(summary = "Get dashboard graph", description = "Get dashboard graph")
    @ApiResponse(responseCode = "200", description = "Dashboard graph retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error retrieving dashboard graph")
    @GetMapping("")
    public Mono<ResponseEntity<ResponseWrapper<List<DashboardGraphDto>>>> getDashboardGraph(
            @RequestHeader("Authorization") String authHeader
    ) {
        return handleOperation(this.dashboardService.getDashboardGraph(authHeader),
                HttpStatus.OK,
                "Dashboard graph retrieved successfully");
    }

}
