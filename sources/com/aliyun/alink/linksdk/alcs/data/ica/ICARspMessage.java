package com.aliyun.alink.linksdk.alcs.data.ica;

/* loaded from: classes2.dex */
public class ICARspMessage {
    public static final int IS_NOTIFY = 1;
    public static final int NOT_NOTIFY = 0;
    public int cbContext;
    public int code;
    public ICADeviceInfo deviceInfo;
    public byte[] payload;

    public ICARspMessage(int i2) {
        this.code = i2;
    }

    public ICARspMessage() {
    }
}
