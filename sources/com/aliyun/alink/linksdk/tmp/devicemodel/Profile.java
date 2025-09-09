package com.aliyun.alink.linksdk.tmp.devicemodel;

/* loaded from: classes2.dex */
public class Profile {
    public String addr;
    private String deviceName;
    public int port;
    private String productKey;

    public String getName() {
        return this.deviceName;
    }

    public String getProdKey() {
        return this.productKey;
    }

    public void setName(String str) {
        this.deviceName = str;
    }

    public void setProdKey(String str) {
        this.productKey = str;
    }
}
