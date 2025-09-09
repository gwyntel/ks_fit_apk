package com.xiaomi.clientreport.manager;

import com.xiaomi.clientreport.data.EventClientReport;

/* loaded from: classes4.dex */
class b implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ EventClientReport f23341a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ a f92a;

    b(a aVar, EventClientReport eventClientReport) {
        this.f92a = aVar;
        this.f23341a = eventClientReport;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f92a.b(this.f23341a);
    }
}
