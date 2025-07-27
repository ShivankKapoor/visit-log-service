package com.shivankkapoor.visit_log_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class DiscordMessagingService {

    private static final Logger logger = LoggerFactory.getLogger(DiscordMessagingService.class);

    @Value("${DISCORD_WEBHOOK_URL:}")
    private String discordWebhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public void sendMessage(String message) {
        if (discordWebhookUrl == null || discordWebhookUrl.isEmpty()) {
            logger.info("DISCORD_WEBHOOK_URL not defined. Skipping Discord message.");
            return;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("content", message);

        try {
            restTemplate.postForObject(discordWebhookUrl, payload, String.class);
            logger.info("Sent message to Discord: {}", message);
        } catch (Exception e) {
            logger.error("Failed to send Discord message: {}", e.getMessage(), e);
        }
    }
}
