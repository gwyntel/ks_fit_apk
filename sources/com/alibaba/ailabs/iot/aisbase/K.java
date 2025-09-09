package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.auth.AuthPluginBusinessProxy;

/* loaded from: classes2.dex */
public class K implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ AuthPluginBusinessProxy f8299a;

    public K(AuthPluginBusinessProxy authPluginBusinessProxy) {
        this.f8299a = authPluginBusinessProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
    }
}
