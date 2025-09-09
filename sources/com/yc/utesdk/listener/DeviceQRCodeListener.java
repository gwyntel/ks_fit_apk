package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface DeviceQRCodeListener {
    public static final int FAIL = 3;
    public static final int START = 1;
    public static final int SUCCESS = 2;

    void onDeviceQRCodeState(int i2);
}
