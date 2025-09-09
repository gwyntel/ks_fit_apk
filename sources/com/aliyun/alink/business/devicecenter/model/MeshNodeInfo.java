package com.aliyun.alink.business.devicecenter.model;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class MeshNodeInfo implements Serializable {
    public String deviceKey;
    public String mac;
    public int netKeyIndex;
    public int pid;
    public int primaryUnicastAddress;
    public String productKey;
    public int retryCount;
    public String subDeviceId;
    public boolean unAcknowledged;

    public String getDeviceKey() {
        return this.deviceKey;
    }

    public String getMac() {
        return this.mac;
    }

    public int getNetKeyIndex() {
        return this.netKeyIndex;
    }

    public int getPid() {
        return this.pid;
    }

    public int getPrimaryUnicastAddress() {
        return this.primaryUnicastAddress;
    }

    public String getProductKey() {
        return this.productKey;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public String getSubDeviceId() {
        return this.subDeviceId;
    }

    public boolean isUnAcknowledged() {
        return this.unAcknowledged;
    }

    public void setDeviceKey(String str) {
        this.deviceKey = str;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public void setNetKeyIndex(int i2) {
        this.netKeyIndex = i2;
    }

    public void setPid(int i2) {
        this.pid = i2;
    }

    public void setPrimaryUnicastAddress(int i2) {
        this.primaryUnicastAddress = i2;
    }

    public void setProductKey(String str) {
        this.productKey = str;
    }

    public void setRetryCount(int i2) {
        this.retryCount = i2;
    }

    public void setSubDeviceId(String str) {
        this.subDeviceId = str;
    }

    public void setUnAcknowledged(boolean z2) {
        this.unAcknowledged = z2;
    }
}
