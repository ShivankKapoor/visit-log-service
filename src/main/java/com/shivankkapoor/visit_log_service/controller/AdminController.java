package com.shivankkapoor.visit_log_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shivankkapoor.visit_log_service.service.DailyVisitSummaryService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Value("${DEV_MODE:false}")
    private boolean devMode;

    @Autowired
    private DailyVisitSummaryService DailyVisitSummaryService;

    @GetMapping("/run-summary")
    public ResponseEntity<String> runSummary() {
        if (devMode) {
            DailyVisitSummaryService.generateDailyVisitSummary();
            logger.info("Forced daily visit summary generation in dev mode");
            return ResponseEntity.status(403).body("Forced daily visit summary generation in dev mode");
        }
        logger.warn("Attempt to run summary in production mode");
        return ResponseEntity.status(403).body("This endpoint is disabled in production mode");
    }
}
