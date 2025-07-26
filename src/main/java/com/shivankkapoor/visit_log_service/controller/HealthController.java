package com.shivankkapoor.visit_log_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shivankkapoor.visit_log_service.model.ApiResponse;

@RestController
@RequestMapping("/health")
public class HealthController {
    @GetMapping("")
    public ResponseEntity<ApiResponse> healthCheck() {
        return ResponseEntity.ok(new ApiResponse(true, "Service is healthy"));
    }
}
