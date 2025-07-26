package com.shivankkapoor.visit_log_service.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.shivankkapoor.visit_log_service.service.ClientIpExtractorService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class OriginFilter implements Filter {

    private static final List<String> FILTERED_ENDPOINTS = Arrays.asList("/track");
    private static final Logger logger = LoggerFactory.getLogger(OriginFilter.class);

    @Value("${DEV_MODE:false}")
    private boolean devMode;

    @Value("${ALLOWED_HOSTS:}")
    private String allowedHosts;

    @Autowired
    private ClientIpExtractorService ipExtractorService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;
        HttpServletResponse httpResp = (HttpServletResponse) response;
        String path = httpReq.getRequestURI();
        String method = httpReq.getMethod();

        boolean shouldFilter = FILTERED_ENDPOINTS.stream()
                .anyMatch(endpoint -> path != null && path.startsWith(endpoint));

        if (!shouldFilter) {
            chain.doFilter(request, response);
            return;
        }

        String origin = httpReq.getHeader("Origin");
        String ip = ipExtractorService.extractClientIp(httpReq);

        if (!devMode) {
            if (origin == null || origin.isEmpty()) {
                logger.warn("Blocked request with missing Origin header from IP: {}", ip);
                httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResp.getWriter().write("Forbidden: Missing Origin header");
                return;
            }

            List<String> allowedOrigins = Arrays.asList(allowedHosts.split(","));
            if (allowedHosts != null && !allowedHosts.isEmpty() && !allowedOrigins.contains(origin)) {
                logger.warn("Blocked request from origin '{}' not in allowed hosts list", origin);
                httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                httpResp.getWriter().write("Forbidden: Origin not allowed");
                return;
            }
        }

        if (origin != null) {
            httpResp.setHeader("Access-Control-Allow-Origin", origin);
            httpResp.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
            httpResp.setHeader("Access-Control-Allow-Headers", "Content-Type");
            httpResp.setHeader("Access-Control-Allow-Credentials", "true");
            httpResp.setHeader("Access-Control-Max-Age", "3600");

            if (devMode) {
                logger.debug("Set CORS headers for origin '{}' (DEV MODE)", origin);
            } else {
                logger.debug("Set CORS headers for allowed origin '{}'", origin);
            }
        }

        // Handle OPTIONS preflight requests
        if ("OPTIONS".equalsIgnoreCase(method)) {
            logger.info("Handling OPTIONS preflight request from origin: '{}', IP: {}", origin, ip);
            httpResp.setStatus(HttpServletResponse.SC_OK);
            return; // Don't continue to controller for OPTIONS
        }

        // Continue with the actual request
        logger.debug("Processing {} request to {} from origin: '{}', IP: {}", method, path, origin, ip);
        chain.doFilter(request, response);
    }
}