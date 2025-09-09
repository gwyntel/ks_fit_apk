package com.alibaba.ailabs.iot.bluetoothlesdk;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;

/* loaded from: classes2.dex */
public class SmartSpeakerBLEDevice extends BluetoothDeviceWrapper {
    public static int GENIE_SMART_SPEAKER_BLE = 8;
    private static final String TAG = "SmartSpeakerBLEDevice";
    private String netConfigFlag;
    private String wifiMacAddress;

    public SmartSpeakerBLEDevice(BluetoothDevice bluetoothDevice, String str, String str2) {
        this.netConfigFlag = str;
        setBluetoothDevice(bluetoothDevice);
        this.wifiMacAddress = str2;
    }

    public String getNetConfigFlag() {
        return this.netConfigFlag;
    }

    public String getWifiMacAddress() {
        return this.wifiMacAddress;
    }

    public void setNetConfigFlag(String str) {
        this.netConfigFlag = str;
    }
}
