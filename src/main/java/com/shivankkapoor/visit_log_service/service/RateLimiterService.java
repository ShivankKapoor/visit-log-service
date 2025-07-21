package com.shivankkapoor.visit_log_service.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimiterService {

    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(15)));
        return Bucket.builder().addLimit(limit).build();
    }

    public boolean tryConsume(String key) {
        return cache.computeIfAbsent(key, k -> createNewBucket()).tryConsume(1);
    }
}
