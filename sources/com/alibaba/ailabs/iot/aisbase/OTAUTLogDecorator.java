package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;

/* loaded from: classes2.dex */
public class OTAUTLogDecorator implements IOTAPlugin.IOTAActionListener {

    /* renamed from: a, reason: collision with root package name */
    public IOTAPlugin.IOTAActionListener f8310a;

    /* renamed from: b, reason: collision with root package name */
    public BluetoothDeviceWrapper f8311b;

    public OTAUTLogDecorator(IOTAPlugin.IOTAActionListener iOTAActionListener, BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        this.f8310a = iOTAActionListener;
        this.f8311b = bluetoothDeviceWrapper;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onFailed(int i2, String str) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8310a;
        if (iOTAActionListener != null) {
            iOTAActionListener.onFailed(i2, str);
        }
        BluetoothDeviceWrapper bluetoothDeviceWrapper = this.f8311b;
        if (bluetoothDeviceWrapper != null) {
            UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(bluetoothDeviceWrapper), UTLogUtils.buildOtaBusInfo("error", 0, i2, str));
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onProgress(int i2, int i3) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8310a;
        if (iOTAActionListener != null) {
            iOTAActionListener.onProgress(i2, i3);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onStateChanged(IOTAPlugin.OTAState oTAState) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8310a;
        if (iOTAActionListener != null) {
            iOTAActionListener.onStateChanged(oTAState);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IOTAActionListener
    public void onSuccess(String str) {
        IOTAPlugin.IOTAActionListener iOTAActionListener = this.f8310a;
        if (iOTAActionListener != null) {
            iOTAActionListener.onSuccess(str);
        }
        UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(this.f8311b), UTLogUtils.buildOtaBusInfo("success", 0, 0, ""));
    }
}
