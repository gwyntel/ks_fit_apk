package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;

/* loaded from: classes2.dex */
public class L implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8302a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8303b;

    public L(AuthPluginBusinessProxy authPluginBusinessProxy, IActionListener iActionListener) {
        this.f8303b = authPluginBusinessProxy;
        this.f8302a = iActionListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        this.f8302a.onFailure(i2, "");
    }
}
