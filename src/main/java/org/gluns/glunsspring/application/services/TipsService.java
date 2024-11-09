package org.gluns.glunsspring.application.services;

import lombok.AllArgsConstructor;
import org.gluns.glunsspring.application.ports.TipsHandlerPort;
import org.springframework.stereotype.Service;

/**
 * DashboardService class.
 * Used to define the DashboardService object.
 */
@AllArgsConstructor
@Service
public class TipsService {
    
    private final TipsHandlerPort tipsHandlerPort;
    
}
