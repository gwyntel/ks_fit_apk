package com.alibaba.ailabs.iot.aisbase;

import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;

/* loaded from: classes2.dex */
public class Ka implements IOTAPlugin.IFirmwareDownloadListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IOTAPlugin.IFirmwareDownloadListener f8300a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BluetoothDeviceWrapper f8301b;

    public Ka(BluetoothDeviceWrapper bluetoothDeviceWrapper, IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener) {
        this.f8301b = bluetoothDeviceWrapper;
        this.f8300a = iFirmwareDownloadListener;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
    public void onComplete(String str) {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = this.f8300a;
        if (iFirmwareDownloadListener != null) {
            iFirmwareDownloadListener.onComplete(str);
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
    public void onDownloadStart() {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = this.f8300a;
        if (iFirmwareDownloadListener != null) {
            iFirmwareDownloadListener.onDownloadStart();
        }
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
    public void onFailed(int i2, String str) {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = this.f8300a;
        if (iFirmwareDownloadListener != null) {
            iFirmwareDownloadListener.onFailed(i2, str);
        }
        UTLogUtils.updateBusInfo("ota", UTLogUtils.buildDeviceInfo(this.f8301b), UTLogUtils.buildOtaBusInfo("error", 0, i2, str));
    }

    @Override // com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin.IFirmwareDownloadListener
    public void onProgress(int i2, int i3) {
        IOTAPlugin.IFirmwareDownloadListener iFirmwareDownloadListener = this.f8300a;
        if (iFirmwareDownloadListener != null) {
            iFirmwareDownloadListener.onProgress(i2, i3);
        }
    }
}
