package com.aliyun.alink.business.devicecenter.cache;

import android.text.TextUtils;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceSubtype;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;
import com.aliyun.alink.business.devicecenter.api.add.ICacheModel;
import com.aliyun.alink.business.devicecenter.base.AlinkHelper;
import com.aliyun.alink.business.devicecenter.utils.StringUtils;

/* loaded from: classes2.dex */
public class EnrolleeTgBleDeviceCacheModel implements ICacheModel {

    /* renamed from: a, reason: collision with root package name */
    public BluetoothDeviceWrapper f10215a;

    /* renamed from: b, reason: collision with root package name */
    public BluetoothDeviceSubtype f10216b;

    public EnrolleeTgBleDeviceCacheModel(BluetoothDeviceWrapper bluetoothDeviceWrapper, BluetoothDeviceSubtype bluetoothDeviceSubtype) {
        this.f10215a = bluetoothDeviceWrapper;
        this.f10216b = bluetoothDeviceSubtype;
    }

    public static String getMacAddressWithColon(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        if (bluetoothDeviceWrapper == null) {
            return null;
        }
        if (bluetoothDeviceWrapper.getAddress() != null) {
            return bluetoothDeviceWrapper.getAddress().toLowerCase();
        }
        if (bluetoothDeviceWrapper.getAisManufactureDataADV() == null) {
            return null;
        }
        String strBytesToHexString = StringUtils.bytesToHexString(bluetoothDeviceWrapper.getAisManufactureDataADV().getMacAddress());
        if (TextUtils.isEmpty(strBytesToHexString)) {
            return null;
        }
        return AlinkHelper.getMacFromSimpleMac(strBytesToHexString.toLowerCase());
    }

    public BluetoothDeviceWrapper getBluetoothDeviceWrapper() {
        return this.f10215a;
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.ICacheModel
    public String getKey() {
        return getMacAddressWithColon(this.f10215a);
    }

    @Override // com.aliyun.alink.business.devicecenter.api.add.ICacheModel
    public boolean isValid() {
        return !TextUtils.isEmpty(getMacAddressWithColon(this.f10215a));
    }
}
