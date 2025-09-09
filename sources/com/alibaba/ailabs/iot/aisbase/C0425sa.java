package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* renamed from: com.alibaba.ailabs.iot.aisbase.sa, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0425sa implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0427ta f8517a;

    public C0425sa(C0427ta c0427ta) {
        this.f8517a = c0427ta;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }
}
