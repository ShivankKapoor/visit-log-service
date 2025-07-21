package com.shivankkapoor.visit_log_service.config;

import com.shivankkapoor.visit_log_service.service.ClientIpExtractorService;
import com.shivankkapoor.visit_log_service.service.RateLimiterService;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RateLimitingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitingFilter.class);

    private final RateLimiterService rateLimiterService;
    private final ClientIpExtractorService ipExtractorService;

    public RateLimitingFilter(RateLimiterService rateLimiterService, ClientIpExtractorService ipExtractorService) {
        this.rateLimiterService = rateLimiterService;
        this.ipExtractorService = ipExtractorService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String ip = ipExtractorService.extractClientIp(httpRequest);
        logger.info("Request from IP: {}", ip);

        if (!rateLimiterService.tryConsume(ip)) {
            logger.warn("Rate limit exceeded for IP: {}", ip);
            HttpServletResponse httpResp = (HttpServletResponse) response;
            httpResp.setStatus(429);
            httpResp.getWriter().write("Too many requests");
            return;
        }

        chain.doFilter(request, response);
    }
}
