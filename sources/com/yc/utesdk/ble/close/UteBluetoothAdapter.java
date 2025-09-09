package com.yc.utesdk.ble.close;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.Set;

/* loaded from: classes4.dex */
public class UteBluetoothAdapter {
    private BluetoothAdapter bluetoothAdapter;

    public UteBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return this.bluetoothAdapter;
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return this.bluetoothAdapter.getBondedDevices();
    }

    public BluetoothDevice getRemoteDevice(String str) {
        return this.bluetoothAdapter.getRemoteDevice(str);
    }

    public boolean isBluetoothAdapterEmpty() {
        return this.bluetoothAdapter != null;
    }

    public boolean isBluetoothEnable() {
        BluetoothAdapter bluetoothAdapter = this.bluetoothAdapter;
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    }

    public void setBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.bluetoothAdapter = bluetoothAdapter;
    }
}
