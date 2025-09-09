package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;

/* renamed from: com.alibaba.ailabs.iot.aisbase.va, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0431va implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8588a;

    public C0431va(OTAPluginProxy oTAPluginProxy) {
        this.f8588a = oTAPluginProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
    }
}
