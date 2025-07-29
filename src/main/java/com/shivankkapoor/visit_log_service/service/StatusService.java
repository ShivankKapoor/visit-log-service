package com.shivankkapoor.visit_log_service.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class StatusService {
    public Mono<Map<String, Object>> getSystemUptimeReactive() {
        Map<String, Object> uptimeStatus = new HashMap<>();
        long uptimeMillis = ManagementFactory.getRuntimeMXBean().getUptime();
        Duration uptime = Duration.ofMillis(uptimeMillis);
        //uptimeStatus.put("uptimeMillis", uptimeMillis);
        uptimeStatus.put("uptimeFormatted", formatDuration(uptime));
        return Mono.just(uptimeStatus);
    }
    
    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%dd %dh %dm %ds", days, hours, minutes, seconds);
    }
}