package com.xiaomi.push;

import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class aj extends ah.b {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ah f23436a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    aj(ah ahVar, ah.a aVar) {
        super(aVar);
        this.f23436a = ahVar;
    }

    @Override // com.xiaomi.push.ah.b
    void b() {
        synchronized (this.f23436a.f164a) {
            this.f23436a.f165a.remove(super.f23434a.mo224a());
        }
    }
}
