package com.alibaba.ailabs.iot.aisbase;

import android.bluetooth.le.ScanResult;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.alibaba.ailabs.iot.aisbase.m, reason: case insensitive filesystem */
/* loaded from: classes2.dex */
public class RunnableC0412m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    public final /* synthetic */ List f8437a;

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0416o f8438b;

    public RunnableC0412m(C0416o c0416o, List list) {
        this.f8438b = c0416o;
        this.f8437a = list;
    }

    @Override // java.lang.Runnable
    public void run() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.f8438b.f8444a > (jElapsedRealtime - this.f8438b.f8445b.f1664g.getReportDelayMillis()) + 5) {
            return;
        }
        this.f8438b.f8444a = jElapsedRealtime;
        ArrayList arrayList = new ArrayList();
        Iterator it = this.f8437a.iterator();
        while (it.hasNext()) {
            arrayList.add(C0418p.this.a((ScanResult) it.next()));
        }
        this.f8438b.f8445b.a(arrayList);
    }
}
