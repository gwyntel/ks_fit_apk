package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class C implements BluetoothProfile.ServiceListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothDevice f8271a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ TransmissionLayerManagerBase f8272b;

    public C(TransmissionLayerManagerBase transmissionLayerManagerBase, BluetoothDevice bluetoothDevice) {
        this.f8272b = transmissionLayerManagerBase;
        this.f8271a = bluetoothDevice;
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
        Class<?> cls = bluetoothA2dp.getClass();
        if (bluetoothA2dp.getConnectionState(this.f8271a) == 2) {
            try {
                try {
                    try {
                        cls.getMethod("disconnect", BluetoothDevice.class).invoke(bluetoothA2dp, this.f8271a);
                    } catch (InvocationTargetException e2) {
                        e2.printStackTrace();
                    }
                } catch (IllegalAccessException e3) {
                    e3.printStackTrace();
                }
            } catch (NoSuchMethodException e4) {
                e4.printStackTrace();
            }
        } else {
            this.f8272b.a(bluetoothA2dp.getConnectionState(this.f8271a));
        }
        BluetoothAdapter.getDefaultAdapter().closeProfileProxy(i2, bluetoothProfile);
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceDisconnected(int i2) {
    }
}
