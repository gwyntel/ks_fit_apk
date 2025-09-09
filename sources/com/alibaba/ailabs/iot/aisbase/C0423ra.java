package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ra, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0423ra implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8515a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8516b;

    public C0423ra(OTAPluginProxy oTAPluginProxy, IActionListener iActionListener) {
        this.f8516b = oTAPluginProxy;
        this.f8515a = iActionListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        IActionListener iActionListener = this.f8515a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, "");
        }
    }
}
