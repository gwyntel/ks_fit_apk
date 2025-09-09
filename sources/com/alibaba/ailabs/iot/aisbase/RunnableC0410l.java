package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.le.ScanResult;
import com.alibaba.ailabs.tg.utils.LogUtils;

/* renamed from: com.alibaba.ailabs.iot.aisbase.l, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0410l implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ScanResult f8433a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0416o f8434b;

    public RunnableC0410l(C0416o c0416o, ScanResult scanResult) {
        this.f8434b = c0416o;
        this.f8433a = scanResult;
    }

    @Override // java.lang.Runnable
    public void run() {
        LogUtils.d("BluetoothLeScannerImplLollipop", "native scan bluetooth device: " + this.f8433a.getDevice().getAddress());
        this.f8434b.f8445b.a(C0418p.this.a(this.f8433a));
    }
}
