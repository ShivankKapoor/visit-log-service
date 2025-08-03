package com.shivankkapoor.visit_log_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DailyVisitSummaryService {

    private static final Logger logger = LoggerFactory.getLogger(DailyVisitSummaryService.class);

    private final RestTemplate restTemplate;
    private final String supabaseUrl;
    private final String supabaseKey;

    public DailyVisitSummaryService(
            RestTemplate restTemplate,
            @Value("${SUPABASE_URL}") String supabaseBaseUrl,
            @Value("${SUPABASE_KEY}") String supabaseKey) {
        this.restTemplate = restTemplate;
        this.supabaseUrl = supabaseBaseUrl + "/rest/v1/rpc/generate_daily_visits_summary";
        this.supabaseKey = supabaseKey;
    }

    public void generateDailyVisitSummary() {
        logger.info("Starting daily visit summary generation...");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", supabaseKey);
        headers.set("Authorization", "Bearer " + supabaseKey);

        HttpEntity<String> entity = new HttpEntity<>("{}", headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(supabaseUrl, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Daily visit summary generated successfully.");
            } else {
                logger.error("Failed to generate daily visit summary: HTTP {}", response.getStatusCode());
                throw new RuntimeException("Failed with HTTP status: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.error("Exception while generating daily visit summary", e);
            throw e;
        }
    }
}
