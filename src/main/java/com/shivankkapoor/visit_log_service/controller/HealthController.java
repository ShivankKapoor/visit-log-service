package com.shivankkapoor.visit_log_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivankkapoor.visit_log_service.model.ApiResponse;
import com.shivankkapoor.visit_log_service.service.StatusService;
import com.shivankkapoor.visit_log_service.service.SupabaseService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private StatusService statusService;

    @Autowired
    private SupabaseService supabaseService;

    @GetMapping("")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(new ApiResponse(true, "Service is healthy"));
    }

    @GetMapping("/status")
    public Mono<ResponseEntity<Map<String, Object>>> getCombinedStatus() {
        Mono<Map<String, Object>> uptimeMono = statusService.getSystemUptimeReactive();
        Mono<Boolean> supabaseMono = supabaseService.checkStatus();

        return Mono.zip(uptimeMono, supabaseMono)
                .map(tuple -> {
                    Map<String, Object> combined = new HashMap<>();
                    combined.put("message", "Status is good");
                    combined.putAll(tuple.getT1()); // uptime map
                    combined.put("supabaseStatus", tuple.getT2() ? "UP" : "DOWN");
                    combined.put("success", tuple.getT2()); // or combine results as needed

                    return ResponseEntity.ok(combined);
                });
    }
}
