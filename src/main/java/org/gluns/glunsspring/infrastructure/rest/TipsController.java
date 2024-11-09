package org.gluns.glunsspring.infrastructure.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gluns.glunsspring.application.services.TipsService;
import org.gluns.glunsspring.infrastructure.rest.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TipsController class.
 * Used to define the TipsController object.
 */
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/tips")
public class TipsController extends BaseController {
    
    private final TipsService tipsService;
    
}
