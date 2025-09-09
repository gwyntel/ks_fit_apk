package com.xiaomi.clientreport.manager;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class f extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ a f23345a;

    f(a aVar) {
        this.f23345a = aVar;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "100889";
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f23345a.b() > 0) {
            this.f23345a.f91a.execute(new g(this));
        }
    }
}
