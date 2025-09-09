package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;

/* loaded from: classes2.dex */
public class D implements BluetoothProfile.ServiceListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ TransmissionLayerManagerBase f8275a;

    public D(TransmissionLayerManagerBase transmissionLayerManagerBase) {
        this.f8275a = transmissionLayerManagerBase;
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) {
        TransmissionLayerManagerBase transmissionLayerManagerBase = this.f8275a;
        transmissionLayerManagerBase.f8380f = transmissionLayerManagerBase.getActiveMethodType((BluetoothA2dp) bluetoothProfile);
        BluetoothAdapter.getDefaultAdapter().closeProfileProxy(i2, bluetoothProfile);
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceDisconnected(int i2) {
    }
}
