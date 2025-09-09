package com.alibaba.ailabs.iot.aisbase.callback;

import android.bluetooth.BluetoothDevice;

/* loaded from: classes2.dex */
public interface ITransmissionLayerCallback {
    void onA2DPConnectionStateUpdate(BluetoothDevice bluetoothDevice, int i2);

    void onBindStateUpdate(BluetoothDevice bluetoothDevice, int i2);

    void onConnectionStateUpdate(BluetoothDevice bluetoothDevice, int i2);

    void onReceivedCommand(byte b2, byte[] bArr);

    void onReceivedStream(byte[] bArr);
}
