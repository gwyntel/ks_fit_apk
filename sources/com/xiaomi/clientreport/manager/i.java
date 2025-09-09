package com.xiaomi.clientreport.manager;

import com.xiaomi.push.bz;

/* loaded from: classes4.dex */
class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f23348a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ bz f95a;

    i(a aVar, bz bzVar) {
        this.f23348a = aVar;
        this.f95a = bzVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f95a.run();
    }
}
