package org.gluns.glunsspring.application.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.gluns.glunsspring.application.factory.TipRequestDummy;
import org.gluns.glunsspring.application.ports.TipsHandlerPort;
import org.gluns.glunsspring.domain.dto.TipsAnswerDto;
import org.gluns.glunsspring.domain.model.ChatContextType;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.TipsAnswerPythonRequest;
import org.gluns.glunsspring.infrastructure.adapters.in.security.JwtTokenExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * DashboardService class.
 * Used to define the DashboardService object.
 */
@AllArgsConstructor
@Service
public class TipsService {

    private static final Logger log = LoggerFactory.getLogger(TipsService.class);
    private final TipsHandlerPort tipsHandlerPort;
    
    private final JwtTokenExtractor jwtTokenExtractor;
    
    private final ObjectMapper objectMapper;
    
    /**
     * getTips
     * Used to get the tips from the Python service.
     * // TODO: Make it dynamic
     * 
     * @param authHeader: Access token
     * @return Mono<List<TipsAnswerDto>>
     */
    public Mono<List<TipsAnswerDto>> getTips(final String authHeader) {
        final List<TipRequestDummy> tipsRequestDummy;
        try {
             tipsRequestDummy = this.objectMapper.readValue(
                     TipsService.class.getClassLoader().getResourceAsStream(".data/.mocks/tips/request-tips.json"),
                     new TypeReference<>() {}
             );
        } catch (Exception e) {
            log.error("Error while reading the tips request", e);
            return Mono.error(e);
        }
        final List<TipsAnswerPythonRequest> tipsAnswerPythonRequest = tipsRequestDummy.stream()
                .map(tipRequestDummy -> new TipsAnswerPythonRequest(
                        0,
                        jwtTokenExtractor.getId(authHeader),
                        ChatContextType.valueOf(tipRequestDummy.context()),
                        tipRequestDummy.input()
                )).toList();        
                
        return Mono.just(tipsAnswerPythonRequest.stream()
                .map(tipsHandlerPort::getAnswer)
                .map(Mono::block)
                .toList());
    }
    
}
