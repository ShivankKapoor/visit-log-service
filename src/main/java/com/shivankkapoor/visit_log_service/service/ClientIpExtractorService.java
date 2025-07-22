package com.shivankkapoor.visit_log_service.service;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ClientIpExtractorService {

    private static final Logger logger = LoggerFactory.getLogger(ClientIpExtractorService.class);

    public String extractClientIp(HttpServletRequest request) {
        String[] headerNames = {
                "x-forwarded-for",
                "cf-connecting-ip",
                "true-client-ip"
        };

        for (String header : headerNames) {
            String ip = request.getHeader(header);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // If x-forwarded-for, take first IP if there are multiple
                if ("x-forwarded-for".equalsIgnoreCase(header) && ip.contains(",")) {
                    ip = ip.split(",")[0].trim();
                }
                logger.info("Client IP found in header {}: {}", header, ip);
                return ip;
            }
        }

        String remoteAddr = request.getRemoteAddr();
        logger.info("Client IP fallback to remoteAddr: {}", remoteAddr);
        return remoteAddr;
    }
}
