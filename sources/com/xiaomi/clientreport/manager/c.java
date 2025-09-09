package com.xiaomi.clientreport.manager;

import com.xiaomi.clientreport.data.PerfClientReport;

/* loaded from: classes4.dex */
class c implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ PerfClientReport f23342a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ a f93a;

    c(a aVar, PerfClientReport perfClientReport) {
        this.f93a = aVar;
        this.f23342a = perfClientReport;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f93a.b(this.f23342a);
    }
}
