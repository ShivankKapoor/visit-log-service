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
import org.springframework.stereotype.Component;

import com.shivankkapoor.visit_log_service.service.ClientIpExtractorService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class OriginFilter implements Filter {

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

        if (path != null && path.startsWith("/health")) {
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

        chain.doFilter(request, response);
    }
}
