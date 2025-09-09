package com.taobao.accs.data;

import com.taobao.accs.ut.monitor.TrafficsMonitor;

/* loaded from: classes4.dex */
class f implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ d f20155a;

    f(d dVar) {
        this.f20155a = dVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        TrafficsMonitor trafficsMonitor = this.f20155a.f20139c;
        if (trafficsMonitor != null) {
            trafficsMonitor.a();
        }
    }
}
