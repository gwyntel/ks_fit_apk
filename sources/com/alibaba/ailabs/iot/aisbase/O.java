package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;

/* loaded from: classes2.dex */
public class O implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ IActionListener f8308a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8309b;

    public O(AuthPluginBusinessProxy authPluginBusinessProxy, IActionListener iActionListener) {
        this.f8309b = authPluginBusinessProxy;
        this.f8308a = iActionListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        IActionListener iActionListener = this.f8308a;
        if (iActionListener != null) {
            iActionListener.onFailure(i2, "");
        }
    }
}
