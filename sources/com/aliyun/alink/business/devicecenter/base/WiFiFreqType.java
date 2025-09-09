package com.aliyun.alink.business.devicecenter.base;

/* loaded from: classes2.dex */
public enum WiFiFreqType {
    WIFI_5G("5GHZ"),
    WIFI_2_4G("2.4GHZ"),
    WIFI_UNKNOWN("UNKNOWN");

    public String name;

    WiFiFreqType(String str) {
        this.name = str;
    }

    public String value() {
        return this.name;
    }
}
