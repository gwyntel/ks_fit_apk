package com.yc.utesdk.listener;

/* loaded from: classes4.dex */
public interface CommonInterfaceListener {
    public static final int BLE_TO_SDK = 1;
    public static final int COMMON_INTERFACE_BLE_TO_SDK_FAIL = 4;
    public static final int COMMON_INTERFACE_BLE_TO_SDK_SUCCESS = 3;
    public static final int COMMON_INTERFACE_SDK_TO_BLE_FAIL = 2;
    public static final int COMMON_INTERFACE_SDK_TO_BLE_SUCCESS = 1;
    public static final int SDK_TO_BLE = 0;

    void onCommonInterfaceBleToSdk(int i2, byte[] bArr);

    void onCommonInterfaceSdkToBle(int i2);
}
