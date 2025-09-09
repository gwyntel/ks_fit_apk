package com.alibaba.ailabs.iot.aisbase;

import aisscanner.ScanResult;
import com.alibaba.ailabs.iot.aisbase.scanner.BLEScannerProxy;

/* loaded from: classes2.dex */
public class Ga implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ScanResult f8287a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ BLEScannerProxy.a f8288b;

    public Ga(BLEScannerProxy.a aVar, ScanResult scanResult) {
        this.f8288b = aVar;
        this.f8287a = scanResult;
    }

    @Override // java.lang.Runnable
    public void run() {
        BLEScannerProxy.f8520c.onMeshNetworkPDURecevied(this.f8287a);
    }
}
