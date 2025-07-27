package com.shivankkapoor.visit_log_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiscordMessageDTO {
    private String ip;
    private String page;
    private String location;

    @Override
    public String toString() {
        if (page.equals("/")) {
           this.page = ("/home");
        }
        return String.format("🌐 Visitor from %s (%s) viewed the page: %s", location, ip, page);
    }
}
