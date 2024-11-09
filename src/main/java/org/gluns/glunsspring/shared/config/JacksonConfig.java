package org.gluns.glunsspring.shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * JacksonConfig
 * Used to define the JacksonConfig object.
 *
 * @version 1.0
 */
@Configuration
public class JacksonConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
}
