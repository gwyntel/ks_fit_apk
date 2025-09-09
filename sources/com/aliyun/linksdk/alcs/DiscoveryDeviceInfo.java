package com.aliyun.linksdk.alcs;

import java.net.InetAddress;

/* loaded from: classes3.dex */
public class DiscoveryDeviceInfo {
    private InetAddress address;
    private String payload;
    private int port;

    public InetAddress getAddress() {
        return this.address;
    }

    public String getPayload() {
        return this.payload;
    }

    public int getPort() {
        return this.port;
    }

    public void setAddress(InetAddress inetAddress) {
        this.address = inetAddress;
    }

    public void setPayload(String str) {
        this.payload = str;
    }

    public void setPort(int i2) {
        this.port = i2;
    }
}
