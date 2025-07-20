package com.shivankkapoor.visit_log_service.model;

import jakarta.validation.constraints.NotBlank;

public class VisitPayload {
    @NotBlank
    private String pageVisited;

    @NotBlank
    private String deviceInfo;

    private String userAgent;
    private String referrer;

    // Getters and Setters
    public String getPageVisited() {
        return pageVisited;
    }
    public void setPageVisited(String pageVisited) {
        this.pageVisited = pageVisited;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }
    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getUserAgent() {
        return userAgent;
    }
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferrer() {
        return referrer;
    }
    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }
}
