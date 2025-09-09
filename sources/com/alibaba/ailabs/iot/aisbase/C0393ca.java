package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.plugin.ota.OTAPluginProxy;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.ca, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0393ca implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ OTAPluginProxy f8361a;

    public C0393ca(OTAPluginProxy oTAPluginProxy) {
        this.f8361a = oTAPluginProxy;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        LogUtils.e(this.f8361a.f8485a, "Send OTA PDU failed: PDU index: " + this.f8361a.f8499o);
        if (this.f8361a.f8504t.getConnectionState() == LayerState.CONNECTED) {
            this.f8361a.f();
        } else {
            this.f8361a.b(0, "Bluetooth connection has been disconnected");
        }
    }
}
