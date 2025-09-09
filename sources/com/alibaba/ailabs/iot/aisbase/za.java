package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.IOTAPlugin;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* loaded from: classes2.dex */
public class za implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8596a;

    public za(OTAPluginProxy oTAPluginProxy) {
        this.f8596a = oTAPluginProxy;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
        LogUtils.d(this.f8596a.f8485a, "Send request OTA success");
        this.f8596a.a(IOTAPlugin.OTAState.WAIT_REQUEST_RESPONSE);
    }
}
