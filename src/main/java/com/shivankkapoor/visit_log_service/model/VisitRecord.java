package com.shivankkapoor.visit_log_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VisitRecord {

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("page_visited")
    private String pageVisited;

    @JsonProperty("device_info")
    private String deviceInfo;

    public VisitRecord(String ipAddress, String pageVisited, String deviceInfo) {
        this.ipAddress = ipAddress;
        this.pageVisited = pageVisited;
        this.deviceInfo = deviceInfo;
    }

    // Optional: getters & setters if needed
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

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
}
