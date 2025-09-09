package com.alibaba.ailabs.iot.aisbase;

import aisscanner.BluetoothLeScannerCompat;
import aisscanner.ScanResult;

/* renamed from: com.alibaba.ailabs.iot.aisbase.g, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0400g implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothLeScannerCompat.a f8404a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ ScanResult f8405b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C0402h f8406c;

    public RunnableC0400g(C0402h c0402h, BluetoothLeScannerCompat.a aVar, ScanResult scanResult) {
        this.f8406c = c0402h;
        this.f8404a = aVar;
        this.f8405b = scanResult;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8404a.a(this.f8405b);
    }
}
