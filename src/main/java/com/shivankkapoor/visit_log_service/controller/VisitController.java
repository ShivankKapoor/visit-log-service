package com.shivankkapoor.visit_log_service.controller;

import com.shivankkapoor.visit_log_service.model.ApiResponse;
import com.shivankkapoor.visit_log_service.model.VisitPayload;
import com.shivankkapoor.visit_log_service.service.ClientIpExtractorService;
import com.shivankkapoor.visit_log_service.service.SupabaseService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class VisitController {

    @Autowired
    private SupabaseService supabaseService;

    @Autowired
    private ClientIpExtractorService ipExtractorService;

    @PostMapping("track")
    public ResponseEntity<ApiResponse> trackVisit(@Valid @RequestBody VisitPayload payload,
            HttpServletRequest request) {
        String clientIp = ipExtractorService.extractClientIp(request);
        supabaseService.storeVisit(clientIp, payload)
                .subscribe();
        return ResponseEntity.ok(new ApiResponse(true, "Visit recorded successfully"));
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to the Visit Service!";
    }
}
