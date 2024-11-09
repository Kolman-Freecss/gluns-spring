package org.gluns.glunsspring.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import org.gluns.glunsspring.infrastructure.adapters.in.security.JwtTokenExtractor;
import org.gluns.glunsspring.infrastructure.adapters.out.keycloak.KeycloakRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/iam")
@Slf4j
public class KeycloakController {
    @Autowired
    private KeycloakRestService restService;

    @Autowired
    private JwtTokenExtractor jwtTokenExtractor;

    @Operation(summary = "Login user", description = "Login user")
    @ApiResponse(responseCode = "200", description = "Login OK")
    @ApiResponse(responseCode = "500", description = "Login error")
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestParam("username") String username,
            @RequestParam("password") String password) {
        String login = restService.login(username, password);
        return ResponseEntity.ok(login);
    }
    // TODO: add validation token

    @Operation(summary = "Read JWT", description = "Read JWT")
    @ApiResponse(responseCode = "200", description = "Read JWT OK")
    @ApiResponse(responseCode = "500", description = "Read JWT error")
    @GetMapping(value = "/login/token/id", produces = MediaType.APPLICATION_JSON_VALUE)
    @Hidden // comment this line to expose the endpoint
    public ResponseEntity<?> getUserId(@RequestHeader("Authorization") String authHeader) {
        String userId = "not found";
        try {
            userId = jwtTokenExtractor.getId(authHeader);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            log.error("userId not found", e);
        }
        return ResponseEntity.ok(userId);
    }
}
