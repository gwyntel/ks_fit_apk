package com.xiaomi.push;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class ci extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ cf f23535a;

    ci(cf cfVar) {
        this.f23535a = cfVar;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return "10053";
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f23535a.f231a != null) {
            this.f23535a.f231a.b(this.f23535a.f228a);
            this.f23535a.b("delete_time");
        }
    }
}
