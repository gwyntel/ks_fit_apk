package com.alibaba.ailabs.iot.aisbase;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanResult;
import android.os.SystemClock;
import java.util.Iterator;

/* renamed from: com.alibaba.ailabs.iot.aisbase.d, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0394d implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothLeScannerCompat.a f8384a;

    public RunnableC0394d(BluetoothLeScannerCompat.a aVar) {
        this.f8384a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        long jElapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        synchronized (this.f8384a.f1658a) {
            try {
                Iterator it = this.f8384a.f1669l.values().iterator();
                while (it.hasNext()) {
                    ScanResult scanResult = (ScanResult) it.next();
                    if (scanResult.getTimestampNanos() < jElapsedRealtimeNanos - this.f8384a.f1664g.getMatchLostDeviceTimeout()) {
                        it.remove();
                        this.f8384a.f1666i.post(new RunnableC0392c(this, scanResult));
                    }
                }
                if (!this.f8384a.f1669l.isEmpty()) {
                    BluetoothLeScannerCompat.a aVar = this.f8384a;
                    aVar.f1666i.postDelayed(this, aVar.f1664g.getMatchLostTaskInterval());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
