package org.gluns.glunsspring.infrastructure.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.services.DashboardService;
import org.gluns.glunsspring.infrastructure.rest.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DashboardController class.
 * Used to define the DashboardController object.
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardController extends BaseController {
    
    private final DashboardService dashboardService;
    
}
