package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;

/* renamed from: com.alibaba.ailabs.iot.aisbase.da, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0395da implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ RunnableC0397ea f8385a;

    public C0395da(RunnableC0397ea runnableC0397ea) {
        this.f8385a = runnableC0397ea;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }
}
