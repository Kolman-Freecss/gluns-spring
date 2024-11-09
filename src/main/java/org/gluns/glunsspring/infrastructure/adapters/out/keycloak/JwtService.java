package org.gluns.glunsspring.infrastructure.adapters.out.keycloak;

import java.net.URI;
import java.net.URL;

import org.springframework.stereotype.Service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.UrlJwkProvider;

import org.springframework.beans.factory.annotation.Value;

/**
 * Service for handling JWT tokens
 */
@Service
public class JwtService {
    @Value("${keycloak.jwk-set-uri}")
    private String jwksUrl;
    // TODO: handle if cert changes ever time starts docker container
    @Value("${keycloak.certs-id}")
    private String certsId;

    // TODO: check if we can use cache
    public Jwk getJwk() throws Exception {
        URI uri = new URI(jwksUrl);
        URL url = uri.toURL();
        UrlJwkProvider urlJwkProvider = new UrlJwkProvider(url);
        Jwk get = urlJwkProvider.get(certsId.trim());
        return get;
    }
}
