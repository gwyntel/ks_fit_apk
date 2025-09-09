package com.vivo.push;

import com.vivo.push.e;

/* loaded from: classes4.dex */
final class k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f23176a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ e f23177b;

    k(e eVar, String str) {
        this.f23177b = eVar;
        this.f23176a = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        e.a aVarD = this.f23177b.d(this.f23176a);
        if (aVarD != null) {
            aVarD.a(1003, new Object[0]);
        }
    }
}
