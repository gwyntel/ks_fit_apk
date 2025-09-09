package com.alibaba.sdk.android.httpdns.probe;

/* loaded from: classes2.dex */
public class IPProbeItem {
    String hostName;
    int port;

    public IPProbeItem(String str, int i2) {
        this.hostName = str;
        this.port = i2;
    }

    public String getHostName() {
        return this.hostName;
    }

    public int getPort() {
        return this.port;
    }
}
