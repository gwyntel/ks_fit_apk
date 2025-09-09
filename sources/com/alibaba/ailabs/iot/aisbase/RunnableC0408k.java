package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.le.BluetoothLeScanner;
import com.alibaba.ailabs.iot.aisbase.C0418p;

/* renamed from: com.alibaba.ailabs.iot.aisbase.k, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0408k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothLeScanner f8428a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0418p.a f8429b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C0418p f8430c;

    public RunnableC0408k(C0418p c0418p, BluetoothLeScanner bluetoothLeScanner, C0418p.a aVar) {
        this.f8430c = c0418p;
        this.f8428a = bluetoothLeScanner;
        this.f8429b = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f8428a.stopScan(this.f8429b.f8449o);
        } catch (Exception unused) {
        }
    }
}
