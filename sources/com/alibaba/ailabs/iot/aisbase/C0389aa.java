package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.aa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0389aa implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8356a;

    public C0389aa(OTAPluginProxy oTAPluginProxy) {
        this.f8356a = oTAPluginProxy;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        this.f8356a.b(1, "Send request verify firmware failed, status: " + i2);
    }
}
