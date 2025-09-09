package com.vivo.push;

import com.vivo.push.e;

/* loaded from: classes4.dex */
final class g implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ e.a f23167a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f23168b;

    g(e eVar, e.a aVar) {
        this.f23168b = eVar;
        this.f23167a = aVar;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        if (i2 != 0) {
            this.f23168b.f23148k = null;
            this.f23168b.f23147j.b("APP_TOKEN");
            return;
        }
        Object[] objArrB = this.f23167a.b();
        if (objArrB == null || objArrB.length == 0) {
            com.vivo.push.util.p.a("PushClientManager", "bind app result is null");
        } else {
            this.f23168b.a((String) this.f23167a.b()[0]);
        }
    }
}
