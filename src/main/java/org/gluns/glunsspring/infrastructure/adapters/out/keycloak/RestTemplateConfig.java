package org.gluns.glunsspring.infrastructure.adapters.out.keycloak;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.Bean;

/**
 * RestTemplateConfig class.
 * 
 * This configuration defines a RestTemplate bean necessary to avoid the
 * injection error in Keycloak RestService.
 * Spring will use this bean to inject an instance of RestTemplate
 * wherever it is needed.
 */
@Configuration
public class RestTemplateConfig {
    /**
     * Defines a RestTemplate bean.
     * 
     * @return a new instance of RestTemplate
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}