package com.taobao.accs.utl;

import android.content.Intent;
import anet.channel.appmonitor.AppMonitor;
import com.taobao.accs.base.AccsDataListener;
import com.taobao.accs.ut.monitor.NetPerformanceMonitor;
import com.taobao.accs.utl.ALog;

/* loaded from: classes4.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NetPerformanceMonitor f20374a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f20375b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ String f20376c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ AccsDataListener f20377d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ int f20378e;

    /* renamed from: f, reason: collision with root package name */
    final /* synthetic */ Intent f20379f;

    f(NetPerformanceMonitor netPerformanceMonitor, String str, String str2, AccsDataListener accsDataListener, int i2, Intent intent) {
        this.f20374a = netPerformanceMonitor;
        this.f20375b = str;
        this.f20376c = str2;
        this.f20377d = accsDataListener;
        this.f20378e = i2;
        this.f20379f = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        NetPerformanceMonitor netPerformanceMonitor = this.f20374a;
        if (netPerformanceMonitor != null) {
            netPerformanceMonitor.real_to_bz_date = System.currentTimeMillis();
        }
        ALog.Level level = ALog.Level.D;
        if (ALog.isPrintLog(level) || "accs-impaas".equals(this.f20375b)) {
            ALog.e(a.TAG, "onSendData start dataId:" + this.f20376c + " serviceId:" + this.f20375b, new Object[0]);
        }
        this.f20377d.onSendData(this.f20375b, this.f20376c, this.f20378e, a.c(this.f20379f));
        if (ALog.isPrintLog(level) || "accs-impaas".equals(this.f20375b)) {
            ALog.e(a.TAG, "onSendData end dataId:" + this.f20376c, new Object[0]);
        }
        AppMonitor.getInstance().commitStat(this.f20374a);
    }
}
