package com.yc.utesdk.scan;

import android.bluetooth.BluetoothDevice;

/* loaded from: classes4.dex */
public class UteScanDevice implements Comparable<UteScanDevice> {

    /* renamed from: a, reason: collision with root package name */
    BluetoothDevice f24932a;

    /* renamed from: b, reason: collision with root package name */
    int f24933b;

    /* renamed from: c, reason: collision with root package name */
    byte[] f24934c;

    public UteScanDevice(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        this.f24932a = bluetoothDevice;
        this.f24933b = i2;
        this.f24934c = bArr;
    }

    @Override // java.lang.Comparable
    public int compareTo(UteScanDevice uteScanDevice) {
        return uteScanDevice.getRssi() - getRssi();
    }

    public BluetoothDevice getDevice() {
        return this.f24932a;
    }

    public int getRssi() {
        return this.f24933b;
    }

    public byte[] getScanRecord() {
        return this.f24934c;
    }

    public void setDevice(BluetoothDevice bluetoothDevice) {
        this.f24932a = bluetoothDevice;
    }

    public void setRssi(int i2) {
        this.f24933b = i2;
    }

    public void setScanRecord(byte[] bArr) {
        this.f24934c = bArr;
    }
}
