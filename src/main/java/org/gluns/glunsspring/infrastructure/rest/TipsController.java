package org.gluns.glunsspring.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.services.TipsService;
import org.gluns.glunsspring.domain.dto.TipsAnswerDto;
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
 * TipsController class.
 * Used to define the TipsController object.
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/tips")
public class TipsController extends BaseController {
    
    private final TipsService tipsService;
    
    /**
     * Get tips.
     * Endpoint operation used by the user to get the tips.
     *
     * @param authHeader
     */
    @Operation(summary = "Get tips", description = "Get tips")
    @ApiResponse(responseCode = "200", description = "Tips retrieved successfully")
    @ApiResponse(responseCode = "500", description = "Error retrieving tips")
    @GetMapping("")
    public Mono<ResponseEntity<ResponseWrapper<List<TipsAnswerDto>>>> getTips(
            @RequestHeader("Authorization") String authHeader
    ) {
        return handleOperation(this.tipsService.getTips(authHeader),
                HttpStatus.OK,
                "Tips retrieved successfully");
    }
    
}
