package org.gluns.glunsspring.application.services;

import lombok.AllArgsConstructor;
import org.gluns.glunsspring.application.ports.DashboardHandlerPort;
import org.springframework.stereotype.Service;

/**
 * DashboardService class.
 * Used to define the DashboardService object.
 */
@AllArgsConstructor
@Service
public class DashboardService {
    
    private final DashboardHandlerPort dashboardHandlerPort;
    
}
