package com.xiaomi.clientreport.manager;

import com.xiaomi.push.by;

/* loaded from: classes4.dex */
class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f23347a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ by f94a;

    h(a aVar, by byVar) {
        this.f23347a = aVar;
        this.f94a = byVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f94a.run();
    }
}
