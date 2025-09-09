package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.qa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0421qa implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8514a;

    public C0421qa(OTAPluginProxy oTAPluginProxy) {
        this.f8514a = oTAPluginProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
    }
}
