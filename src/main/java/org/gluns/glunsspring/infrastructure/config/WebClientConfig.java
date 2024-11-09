package org.gluns.glunsspring.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfig
 */
@Configuration
public class WebClientConfig {
    
    public static final int RESPONSE_MAX_SIZE = 10 * (1024 * 1024); // 10 MB
    
    @Bean("pythonWebClient")
    public WebClient webClient(@Value("${gluns.python.url}") final String pythonUrl) {
        return WebClient.builder()
            .baseUrl(pythonUrl)
            .defaultHeaders(httpHeaders -> {
                httpHeaders.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
                httpHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            })
            .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(RESPONSE_MAX_SIZE))
            .build();
    }
    
}

