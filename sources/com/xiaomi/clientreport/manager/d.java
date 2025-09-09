package com.xiaomi.clientreport.manager;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class d extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f23343a;

    d(a aVar) {
        this.f23343a = aVar;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "100888";
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f23343a.a() > 0) {
            this.f23343a.f91a.execute(new e(this));
        }
    }
}
