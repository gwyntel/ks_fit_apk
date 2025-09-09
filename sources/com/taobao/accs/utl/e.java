package com.taobao.accs.utl;

import android.content.Intent;
import anet.channel.appmonitor.AppMonitor;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NetPerformanceMonitor f20367a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20368b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f20369c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20370d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ int f20371e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ byte[] f20372f;

    /* renamed from: g, reason: collision with root package name */
    final /* synthetic */ Intent f20373g;

    e(NetPerformanceMonitor netPerformanceMonitor, String str, String str2, AccsDataListener accsDataListener, int i2, byte[] bArr, Intent intent) {
        this.f20367a = netPerformanceMonitor;
        this.f20368b = str;
        this.f20369c = str2;
        this.f20370d = accsDataListener;
        this.f20371e = i2;
        this.f20372f = bArr;
        this.f20373g = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        NetPerformanceMonitor netPerformanceMonitor = this.f20367a;
        if (netPerformanceMonitor != null) {
            netPerformanceMonitor.real_to_bz_date = System.currentTimeMillis();
        }
        ALog.Level level = ALog.Level.D;
        if (ALog.isPrintLog(level) || "accs-impaas".equals(this.f20368b)) {
            ALog.e(a.TAG, "onResponse start dataId:" + this.f20369c + " serviceId:" + this.f20368b, new Object[0]);
        }
        this.f20370d.onResponse(this.f20368b, this.f20369c, this.f20371e, this.f20372f, a.c(this.f20373g));
        if (ALog.isPrintLog(level) || "accs-impaas".equals(this.f20368b)) {
            ALog.e(a.TAG, "onResponse end dataId:" + this.f20369c, new Object[0]);
        }
        AppMonitor.getInstance().commitStat(this.f20367a);
    }
}
