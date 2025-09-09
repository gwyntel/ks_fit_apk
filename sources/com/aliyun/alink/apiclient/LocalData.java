package com.aliyun.alink.apiclient;

import com.aliyun.alink.apiclient.model.DeviceAuthInfo;

/* loaded from: classes2.dex */
public class LocalData {
    private String authToken;
    private DeviceAuthInfo deviceData;

    private static class SingletonHolder {
        private static final LocalData INSTANCE = new LocalData();

        private SingletonHolder() {
        }
    }

    public static LocalData getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public DeviceAuthInfo getDeviceData() {
        return this.deviceData;
    }

    public void setAuthToken(String str) {
        this.authToken = str;
    }

    public void setDeviceData(DeviceAuthInfo deviceAuthInfo) {
        this.deviceData = deviceAuthInfo;
    }

    private LocalData() {
        this.deviceData = null;
        this.authToken = null;
    }
}
