package com.alibaba.ailabs.iot.aisbase;

import aisscanner.ScanResult;

/* renamed from: com.alibaba.ailabs.iot.aisbase.c, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0392c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ScanResult f8359a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ RunnableC0394d f8360b;

    public RunnableC0392c(RunnableC0394d runnableC0394d, ScanResult scanResult) {
        this.f8360b = runnableC0394d;
        this.f8359a = scanResult;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8360b.f8384a.f1665h.onScanResult(4, this.f8359a);
    }
}
