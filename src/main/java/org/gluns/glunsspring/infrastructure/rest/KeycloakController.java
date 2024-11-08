package org.gluns.glunsspring.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.gluns.glunsspring.infrastructure.adapters.out.keycloak.KeycloakRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/iam")
public class KeycloakController {
    @Autowired
    private KeycloakRestService restService;

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
}
