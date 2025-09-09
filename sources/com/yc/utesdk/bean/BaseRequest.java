package com.yc.utesdk.bean;

import com.google.gson.Gson;

/* loaded from: classes4.dex */
public class BaseRequest {
    private String app_key;
    private String mac;

    public String getApp_key() {
        return this.app_key;
    }

    public String getMac() {
        return this.mac;
    }

    public void setApp_key(String str) {
        this.app_key = str;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public String toString() {
        return new Gson().toJson(this);
    }
}
