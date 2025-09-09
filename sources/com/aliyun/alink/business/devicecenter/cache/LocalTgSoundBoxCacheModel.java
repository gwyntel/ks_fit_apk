package com.aliyun.alink.business.devicecenter.cache;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.bluetoothlesdk.SmartSpeakerBLEDevice;
import com.aliyun.alink.business.devicecenter.api.add.ICacheModel;

/* loaded from: classes2.dex */
public class LocalTgSoundBoxCacheModel implements ICacheModel {

    /* renamed from: a, reason: collision with root package name */
    public SmartSpeakerBLEDevice f10217a;

    /* renamed from: b, reason: collision with root package name */
    public BluetoothDeviceSubtype f10218b;

    public LocalTgSoundBoxCacheModel(SmartSpeakerBLEDevice smartSpeakerBLEDevice, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        this.f10217a = smartSpeakerBLEDevice;
        this.f10218b = bluetoothDeviceSubtype;
    }

    public BluetoothDeviceSubtype getBluetoothDeviceSubtype() {
        return this.f10218b;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.ICacheModel
    public String getKey() {
        return this.f10217a.getWifiMacAddress().toLowerCase();
    }

    public SmartSpeakerBLEDevice getSpeakerBLEDevice() {
        return this.f10217a;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.ICacheModel
    public boolean isValid() {
        return !TextUtils.isEmpty(this.f10217a.getWifiMacAddress());
    }

    public void setBluetoothDeviceSubtype(BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        this.f10218b = bluetoothDeviceSubtype;
    }

    public void setSpeakerBLEDevice(SmartSpeakerBLEDevice smartSpeakerBLEDevice) {
        this.f10217a = smartSpeakerBLEDevice;
    }
}
