package com.alibaba.ailabs.iot.aisbase;

import aisscanner.BluetoothLeScannerCompat;

/* renamed from: com.alibaba.ailabs.iot.aisbase.b, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0390b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothLeScannerCompat.a f8357a;

    public RunnableC0390b(BluetoothLeScannerCompat.a aVar) {
        this.f8357a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f8357a.f1662e) {
            return;
        }
        this.f8357a.b();
        BluetoothLeScannerCompat.a aVar = this.f8357a;
        aVar.f1666i.postDelayed(this, aVar.f1664g.getReportDelayMillis());
    }
}
