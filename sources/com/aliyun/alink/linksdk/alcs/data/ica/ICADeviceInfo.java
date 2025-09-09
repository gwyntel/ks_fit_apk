package com.aliyun.alink.linksdk.alcs.data.ica;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class ICADeviceInfo {
    public String deviceName;
    public String ip;
    public String productKey;

    public ICADeviceInfo(String str, String str2) {
        this(str, str2, null);
    }

    public String getDevId() {
        if (TextUtils.isEmpty(this.productKey)) {
            return this.deviceName;
        }
        return this.productKey + this.deviceName;
    }

    public String toString() {
        return this.productKey + this.deviceName;
    }

    public ICADeviceInfo(String str, String str2, String str3) {
        this.productKey = str;
        this.deviceName = str2;
        this.ip = str3;
    }
}
