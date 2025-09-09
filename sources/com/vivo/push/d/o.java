package com.vivo.push.d;

/* loaded from: classes4.dex */
final class o implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.n f23122a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ n f23123b;

    o(n nVar, com.vivo.push.b.n nVar2) {
        this.f23123b = nVar;
        this.f23122a = nVar2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        n nVar = this.f23123b;
        ((z) nVar).f23137b.onLog(((com.vivo.push.l) nVar).f23178a, this.f23122a.d(), this.f23122a.e(), this.f23122a.f());
    }
}
