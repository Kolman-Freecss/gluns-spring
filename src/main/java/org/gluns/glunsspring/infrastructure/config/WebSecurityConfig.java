package org.gluns.glunsspring.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebSecurityConfig
 * Used to define the methods that the WebSecurityConfig must implement.
 */
@Configuration
public class WebSecurityConfig implements WebFluxConfigurer {
    
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
    
}
