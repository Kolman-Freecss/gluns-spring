package org.gluns.glunsspring.infrastructure.adapters.out.keycloak;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

/**
 * KeycloackRestService class.
 */
@Service
@Slf4j
public class KeycloakRestService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${keycloak.token-uri}")
    private String keycloakTokenUri;

    @Value("${keycloak.user-info-uri}")
    private String keycloakUserInfo;

    @Value("${keycloak.logout}")
    private String keycloakLogout;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.authorization-grant-type}")
    private String grantType;

    @Value("${keycloak.authorization-grant-type-refresh}")
    private String grantTypeRefresh;

    /**
     * Login a user by sending a POST request to the Keycloak token endpoint.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return the response from the Keycloak token endpoint as a String
     */
    public String login(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", username);
        map.add("password", password);
        map.add("client_id", clientId);
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, new HttpHeaders());

        log.debug("URL: " + keycloakTokenUri);
        log.debug("Headers: " + request.getHeaders());
        log.debug("Body: " + request.getBody());

        String result = restTemplate.postForObject(keycloakTokenUri, request, String.class);

        // TODO: handle error response
        return result;
    }
}
