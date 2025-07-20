package com.shivankkapoor.visit_log_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.shivankkapoor.visit_log_service.model.VisitPayload;

@Service
public class SupabaseService {

    private final WebClient webClient;

    @Value("${SUPABASE_URL}")
    private String supabaseUrl;

    @Value("${SUPABASE_KEY}")
    private String supabaseKey;

    public SupabaseService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<Void> storeVisit(String ipAddress, VisitPayload payload) {
        return webClient.post()
                .uri(supabaseUrl + "/rest/v1/page_visits")
                .header("apikey", supabaseKey)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Prefer", "return=minimal")
                .bodyValue(new VisitRecord(ipAddress, payload))
                .retrieve()
                .bodyToMono(Void.class);
    }

    private record VisitRecord(String ipAddress, VisitPayload payload) {
        public String ip_address() { return ipAddress; }
        public String page_visited() { return payload.getPageVisited(); }
        public String device_info() { return payload.getDeviceInfo(); }
    }
}

