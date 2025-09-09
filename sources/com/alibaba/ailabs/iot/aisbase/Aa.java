package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* loaded from: classes2.dex */
public class Aa implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8267a;

    public Aa(OTAPluginProxy oTAPluginProxy) {
        this.f8267a = oTAPluginProxy;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        this.f8267a.b(1, "Send request OTA failed, status: " + i2);
    }
}
