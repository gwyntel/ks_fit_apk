package com.alibaba.ailabs.iot.aisbase;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanRecord;
import aisscanner.ScanResult;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.SystemClock;

/* renamed from: com.alibaba.ailabs.iot.aisbase.h, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0402h implements BluetoothAdapter.LeScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ C0404i f8408a;

    public C0402h(C0404i c0404i) {
        this.f8408a = c0404i;
    }

    @Override // android.bluetooth.BluetoothAdapter.LeScanCallback
    public void onLeScan(BluetoothDevice bluetoothDevice, int i2, byte[] bArr) {
        ScanResult scanResult = new ScanResult(bluetoothDevice, ScanRecord.parseFromBytes(bArr), i2, SystemClock.elapsedRealtimeNanos());
        synchronized (this.f8408a.f8410c) {
            try {
                for (BluetoothLeScannerCompat.a aVar : this.f8408a.f8410c.values()) {
                    aVar.f1666i.post(new RunnableC0400g(this, aVar, scanResult));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
