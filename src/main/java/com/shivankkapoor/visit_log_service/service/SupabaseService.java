package com.shivankkapoor.visit_log_service.service;

import com.shivankkapoor.visit_log_service.model.VisitRecord;
import com.shivankkapoor.visit_log_service.model.VisitPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
        VisitRecord visitRecord = new VisitRecord(
                ipAddress,
                payload.getPageVisited(),
                payload.getDeviceInfo());

        return webClient.post()
                .uri(supabaseUrl + "/rest/v1/page_visits")
                .header("apikey", supabaseKey)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("Prefer", "return=minimal")
                .bodyValue(visitRecord)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Boolean> checkStatus() {
        return webClient.get()
                .uri(supabaseUrl + "/health")
                .header("apikey", supabaseKey)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + supabaseKey)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> true)
                .onErrorReturn(false);
    }

}
