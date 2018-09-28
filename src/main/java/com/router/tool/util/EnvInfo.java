package com.router.tool.util;


public class EnvInfo {

    private String vitaminIP;
    private String zkURL;

    EnvInfo() {
    }

    EnvInfo(String vitaminIP, String zkURL) {
        this.vitaminIP = vitaminIP;
        this.zkURL = zkURL;
    }

    public String getVitaminIp() {
        return vitaminIP;
    }

    public String getZkURL() {
        return zkURL;
    }
}