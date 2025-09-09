package com.taobao.accs.data;

import com.taobao.accs.ut.monitor.TrafficsMonitor;

/* loaded from: classes4.dex */
class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ TrafficsMonitor.a f20153a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ d f20154b;

    e(d dVar, TrafficsMonitor.a aVar) {
        this.f20154b = dVar;
        this.f20153a = aVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        TrafficsMonitor trafficsMonitor = this.f20154b.f20139c;
        if (trafficsMonitor != null) {
            trafficsMonitor.a(this.f20153a);
        }
    }
}
