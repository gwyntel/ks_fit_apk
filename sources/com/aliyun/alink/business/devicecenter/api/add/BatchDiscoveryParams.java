package com.aliyun.alink.business.devicecenter.api.add;

import java.io.Serializable;

/* loaded from: classes2.dex */
public class BatchDiscoveryParams implements Serializable {
    public String deviceName;
    public String enrolleeProductKey;
    public String productKey;

    public String toString() {
        return "{\"enrolleeProductKey\":\"" + this.enrolleeProductKey + "\",\"productKey\":\"" + this.productKey + "\",\"deviceName\":\"" + this.deviceName + "\"}";
    }
}
