package com.vivo.push.d;

/* loaded from: classes4.dex */
final class ae implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.i f23105a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ ad f23106b;

    ae(ad adVar, com.vivo.push.b.i iVar) {
        this.f23106b = adVar;
        this.f23105a = iVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ad adVar = this.f23106b;
        ((z) adVar).f23137b.onUnBind(((com.vivo.push.l) adVar).f23178a, this.f23105a.h(), this.f23105a.d());
    }
}
