package org.gluns.glunsspring.application.ports;

import org.gluns.glunsspring.domain.dto.TipsAnswerDto;
import org.gluns.glunsspring.infrastructure.adapters.in.gluns.python.models.TipsAnswerPythonRequest;
import reactor.core.publisher.Mono;

/**
 * TipsHandlerPort
 */
public interface TipsHandlerPort {
    
    Mono<TipsAnswerDto> getAnswer(final TipsAnswerPythonRequest tipsAnswerPythonRequest);
    
}
