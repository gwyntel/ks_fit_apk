package com.aliyun.alink.linksdk.alcs.data.ica;

/* loaded from: classes2.dex */
public class ICAInitData {
    public ICADeviceInfo deviceInfo;
    public int role;

    public ICAInitData(String str, String str2, int i2) {
        this.deviceInfo = new ICADeviceInfo(str, str2);
        this.role = i2;
    }
}
