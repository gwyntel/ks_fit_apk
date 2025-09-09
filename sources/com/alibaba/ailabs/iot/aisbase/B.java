package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import com.alibaba.ailabs.iot.aisbase.channel.TransmissionLayerManagerBase;
import com.taobao.accs.utl.BaseMonitor;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class B implements BluetoothProfile.ServiceListener {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothDevice f8268a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ TransmissionLayerManagerBase f8269b;

    public B(TransmissionLayerManagerBase transmissionLayerManagerBase, BluetoothDevice bluetoothDevice) {
        this.f8269b = transmissionLayerManagerBase;
        this.f8268a = bluetoothDevice;
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceConnected(int i2, BluetoothProfile bluetoothProfile) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        BluetoothA2dp bluetoothA2dp = (BluetoothA2dp) bluetoothProfile;
        Class<?> cls = bluetoothA2dp.getClass();
        int connectionState = bluetoothA2dp.getConnectionState(this.f8268a);
        if (connectionState != 2) {
            try {
                try {
                    try {
                        cls.getMethod(BaseMonitor.ALARM_POINT_CONNECT, BluetoothDevice.class).invoke(bluetoothA2dp, this.f8268a);
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
            this.f8269b.a(connectionState);
        }
        BluetoothDevice bluetoothDeviceA = this.f8269b.a(bluetoothA2dp);
        if (bluetoothDeviceA == null || !bluetoothDeviceA.getAddress().equalsIgnoreCase(this.f8268a.getAddress())) {
            this.f8269b.a(bluetoothA2dp, this.f8268a);
        }
        BluetoothAdapter.getDefaultAdapter().closeProfileProxy(i2, bluetoothProfile);
    }

    @Override // android.bluetooth.BluetoothProfile.ServiceListener
    public void onServiceDisconnected(int i2) {
    }
}
