package com.alibaba.ailabs.iot.aisbase;

import aisble.callback.DataSentCallback;
import aisble.data.Data;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.alibaba.ailabs.iot.aisbase.callback.ICommandSendListener;
import com.alibaba.ailabs.iot.aisbase.plugin.basic.BasicProxy;

/* loaded from: classes2.dex */
public class P implements DataSentCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ICommandSendListener f8314a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BasicProxy f8315b;

    public P(BasicProxy basicProxy, ICommandSendListener iCommandSendListener) {
        this.f8315b = basicProxy;
        this.f8314a = iCommandSendListener;
    }

    @Override // aisble.callback.DataSentCallback
    public void onDataSent(@NonNull BluetoothDevice bluetoothDevice, @NonNull Data data) {
        ICommandSendListener iCommandSendListener = this.f8314a;
        if (iCommandSendListener != null) {
            iCommandSendListener.onSent();
        }
    }
}
