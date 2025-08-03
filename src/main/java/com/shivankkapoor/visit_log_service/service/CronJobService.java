package com.shivankkapoor.visit_log_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class CronJobService {

    private static final Logger logger = LoggerFactory.getLogger(CronJobService.class);

    private final DailyVisitSummaryService dailyVisitSummaryService;

    public CronJobService(DailyVisitSummaryService dailyVisitSummaryService) {
        this.dailyVisitSummaryService = dailyVisitSummaryService;
    }

    @Scheduled(cron = "0 55 23 * * *", zone = "America/Chicago")
    public void scheduledRun() {
        logger.info("Scheduled job: Running daily visit summary");
        dailyVisitSummaryService.generateDailyVisitSummary();
    }

}
