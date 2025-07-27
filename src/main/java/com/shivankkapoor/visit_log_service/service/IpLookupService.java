package com.shivankkapoor.visit_log_service.service;

import com.shivankkapoor.visit_log_service.dto.IpApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpLookupService {

    private static final Logger logger = LoggerFactory.getLogger(IpLookupService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public String getLocation(String ip) {
        try {
            String url = "http://ip-api.com/json/" + ip + "?fields=status,message,country,regionName,city";
            IpApiResponse response = restTemplate.getForObject(url, IpApiResponse.class);
            if (response != null) {
                return String.format("%s, %s, %s",
                        response.getCity(),
                        response.getRegionName(),
                        response.getCountry()
                );
            }
        } catch (Exception e) {
            logger.error("IP Lookup failed for IP: " + ip, e);
        }
        return "Unknown";
    }
}
