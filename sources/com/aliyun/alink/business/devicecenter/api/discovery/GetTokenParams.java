package com.aliyun.alink.business.devicecenter.api.discovery;

import com.alipay.sdk.m.u.i;

/* loaded from: classes2.dex */
public class GetTokenParams {
    public String productKey = null;
    public String deviceName = null;
    public int timeout = 45000;
    public int interval = 5000;

    public String toString() {
        return "{productKey:" + this.productKey + "deviceName:" + this.deviceName + "timeout:" + this.timeout + "interval:" + this.interval + i.f9804d;
    }
}
