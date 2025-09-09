package com.yc.utesdk.ble.open;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

/* loaded from: classes4.dex */
public interface UteBleDevice {
    public static final int DEVICE_PLATFORM_DIALOG = 2;
    public static final int DEVICE_PLATFORM_NRF = 1;
    public static final int DEVICE_PLATFORM_RTL = 0;

    UteBleConnection connect(String str);

    void disconnect();

    BluetoothDevice getBluetoothDevice();

    BluetoothGatt getBluetoothGatt();

    String getDeviceAddress();

    String getDeviceName();

    int getDevicePlatform();

    int getPhoneMtuSize();

    boolean isBluetoothEnable();

    boolean isConnected();

    boolean isConnected(String str);

    boolean isDeviceBusy();

    void setDevicePlatform(int i2);
}
