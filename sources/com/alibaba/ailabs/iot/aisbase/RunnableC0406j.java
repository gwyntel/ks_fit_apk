package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanSettings;
import com.alibaba.ailabs.iot.aisbase.C0418p;
import java.util.List;

/* renamed from: com.alibaba.ailabs.iot.aisbase.j, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0406j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ BluetoothLeScanner f8421a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ List f8422b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ ScanSettings f8423c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ C0418p.a f8424d;

    /* renamed from: e, reason: collision with root package name */
    public final /* synthetic */ C0418p f8425e;

    public RunnableC0406j(C0418p c0418p, BluetoothLeScanner bluetoothLeScanner, List list, ScanSettings scanSettings, C0418p.a aVar) {
        this.f8425e = c0418p;
        this.f8421a = bluetoothLeScanner;
        this.f8422b = list;
        this.f8423c = scanSettings;
        this.f8424d = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f8421a.startScan(this.f8422b, this.f8423c, this.f8424d.f8449o);
    }
}
