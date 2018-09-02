package com.fastjrun.client;

import java.util.ResourceBundle;

import com.fastjrun.util.FastJsonObjectMapper;

public class DefaultAppClient extends DefaultResponseHandleClient {

    protected String appSource;
    protected String appVersion;
    protected String deviceId;
    protected String appKey;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSource() {
        return appSource;
    }

    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    @Override
    public void initSDKConfig() {
        ResourceBundle rb = ResourceBundle.getBundle("api-sdk");
        String baseUrl = rb.getString("appServer.baseUrl");
        this.appKey = rb.getString("appServer.appKey");
        this.appVersion = rb.getString("appServer.appVersion");
        this.appSource = rb.getString("appServer.appSource");
        this.deviceId = rb.getString("appServer.deviceId");
        this.initUtilClient(baseUrl);
        this.objectMapper = new FastJsonObjectMapper();
    }

    @Override
    protected String generateUrlSuffix() {
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(this.appKey);
        sb.append("/").append(this.appVersion);
        sb.append("/").append(this.appSource);
        sb.append("/").append(this.deviceId);
        long txTime = System.currentTimeMillis();
        sb.append("/").append(txTime);
        return sb.toString();
    }
}
