package com.alibaba.ailabs.iot.mesh.bean;

/* loaded from: classes2.dex */
public class ConnectionParams {
    public String deviceId;
    public String directConnectionProxyNodeMacAddress;

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getDirectConnectionProxyNodeMacAddress() {
        return this.directConnectionProxyNodeMacAddress;
    }

    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public void setDirectConnectionProxyNodeMacAddress(String str) {
        this.directConnectionProxyNodeMacAddress = str;
    }

    public String toString() {
        return "ConnectionParams{deviceId='" + this.deviceId + "'}";
    }
}
