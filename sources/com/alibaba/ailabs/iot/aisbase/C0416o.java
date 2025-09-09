package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import com.alibaba.ailabs.iot.aisbase.C0418p;
import java.util.List;

/* renamed from: com.alibaba.ailabs.iot.aisbase.o, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class C0416o extends ScanCallback {

    /* renamed from: a, reason: collision with root package name */
    public long f8444a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0418p.a f8445b;

    public C0416o(C0418p.a aVar) {
        this.f8445b = aVar;
    }

    @Override // android.bluetooth.le.ScanCallback
    public void onBatchScanResults(List<ScanResult> list) {
        this.f8445b.f1666i.post(new RunnableC0412m(this, list));
    }

    @Override // android.bluetooth.le.ScanCallback
    public void onScanFailed(int i2) {
        this.f8445b.f1666i.post(new RunnableC0414n(this, i2));
    }

    @Override // android.bluetooth.le.ScanCallback
    public void onScanResult(int i2, ScanResult scanResult) {
        this.f8445b.f1666i.post(new RunnableC0410l(this, scanResult));
    }
}
