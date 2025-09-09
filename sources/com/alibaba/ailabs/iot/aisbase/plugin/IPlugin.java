package com.alibaba.ailabs.iot.aisbase.plugin;

import aisble.callback.DataSentCallback;
import aisble.callback.FailCallback;
import com.alibaba.ailabs.iot.aisbase.channel.ITransmissionLayer;
import com.alibaba.ailabs.iot.aisbase.exception.UnsupportedLayerException;
import com.alibaba.ailabs.iot.aisbase.spec.AISCommand;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;

/* loaded from: classes2.dex */
public interface IPlugin {
    void enableAESEncryption(byte[] bArr);

    BluetoothDeviceWrapper getBluetoothDeviceWrapper();

    void init(ITransmissionLayer iTransmissionLayer) throws UnsupportedLayerException;

    void onBluetoothConnectionStateChanged(int i2);

    void onDeviceReady();

    AISCommand sendCommandWithCallback(byte b2, byte[] bArr, DataSentCallback dataSentCallback, FailCallback failCallback);

    void sendRawDataWithCallback(byte[] bArr, DataSentCallback dataSentCallback, FailCallback failCallback);

    void setBluetoothDeviceWrapper(BluetoothDeviceWrapper bluetoothDeviceWrapper);

    void start();

    void stop();

    void uninstall();
}
