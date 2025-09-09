package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.basic.BasicProxy;

/* loaded from: classes2.dex */
public class S implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BasicProxy f8328a;

    public S(BasicProxy basicProxy) {
        this.f8328a = basicProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
    }
}
