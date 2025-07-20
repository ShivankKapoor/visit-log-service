package com.shivankkapoor.visit_log_service.dto;

import lombok.Data;

@Data
public class PageVisitDto {
    private String ip_address;
    private String visited_at;
    private String page_visited;
    private String device_info;
}
