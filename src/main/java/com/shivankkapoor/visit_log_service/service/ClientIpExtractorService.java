package com.shivankkapoor.visit_log_service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientIpExtractorService {

    public String extractClientIp(HttpServletRequest request) {
        String forwardedForHeader = request.getHeader("X-Forwarded-For");
        if (forwardedForHeader != null && !forwardedForHeader.isEmpty()) {
            return forwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
