package com.alibaba.ailabs.iot.mesh.callback;

import android.bluetooth.BluetoothDevice;

/* loaded from: classes2.dex */
public interface IConnectCallback {
    void onConnected(BluetoothDevice bluetoothDevice);

    void onFailure(BluetoothDevice bluetoothDevice, int i2, String str);

    void onReady(BluetoothDevice bluetoothDevice);
}
