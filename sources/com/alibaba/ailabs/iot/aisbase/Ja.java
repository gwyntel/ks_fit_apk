package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.spec.BluetoothDeviceWrapper;

/* loaded from: classes2.dex */
public class Ja implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothDeviceWrapper f8298a;

    public Ja(BluetoothDeviceWrapper bluetoothDeviceWrapper) {
        this.f8298a = bluetoothDeviceWrapper;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }
}
