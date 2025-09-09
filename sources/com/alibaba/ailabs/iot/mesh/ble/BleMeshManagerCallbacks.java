package com.alibaba.ailabs.iot.mesh.ble;

import aisble.BleManagerCallbacks;
import android.bluetooth.BluetoothDevice;

/* loaded from: classes2.dex */
public interface BleMeshManagerCallbacks extends BleManagerCallbacks {
    void onDataReceived(BluetoothDevice bluetoothDevice, int i2, byte[] bArr);

    void onDataSent(BluetoothDevice bluetoothDevice, int i2, byte[] bArr);
}
