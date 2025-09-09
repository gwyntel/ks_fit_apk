package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.FailCallback;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.ICommandSendListener;
import com.alibaba.ailabs.iot.aisbase.channel.LayerState;
import com.alibaba.ailabs.iot.aisbase.plugin.basic.BasicProxy;

/* loaded from: classes2.dex */
public class Q implements FailCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ICommandSendListener f8318a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BasicProxy f8319b;

    public Q(BasicProxy basicProxy, ICommandSendListener iCommandSendListener) {
        this.f8319b = basicProxy;
        this.f8318a = iCommandSendListener;
    }

    @Override // aisble.callback.FailCallback
    public void onRequestFailed(@NonNull BluetoothDevice bluetoothDevice, int i2) {
        if (this.f8318a == null) {
            return;
        }
        if (this.f8319b.f8459c.getConnectionState() != LayerState.CONNECTED) {
            this.f8318a.onFailure(0, "");
        } else {
            this.f8318a.onFailure(1, "");
        }
    }
}
