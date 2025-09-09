package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;

/* loaded from: classes2.dex */
public class N implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8307a;

    public N(AuthPluginBusinessProxy authPluginBusinessProxy) {
        this.f8307a = authPluginBusinessProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
    }
}
