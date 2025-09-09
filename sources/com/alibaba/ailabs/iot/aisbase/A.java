package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothDevice;
import com.alibaba.ailabs.iot.aisbase.callback.IActionListener;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;

/* loaded from: classes2.dex */
public class A implements IActionListener<BluetoothDevice> {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ TransmissionLayerManagerBase f8259a;

    public A(TransmissionLayerManagerBase transmissionLayerManagerBase) {
        this.f8259a = transmissionLayerManagerBase;
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onSuccess(BluetoothDevice bluetoothDevice) {
    }

    @Override // com.alibaba.ailabs.iot.aisbase.callback.IActionListener
    public void onFailure(int i2, String str) {
    }
}
