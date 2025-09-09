package com.vivo.push.d;

/* loaded from: classes4.dex */
final class y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.r f23135a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ x f23136b;

    y(x xVar, com.vivo.push.b.r rVar) {
        this.f23136b = xVar;
        this.f23135a = rVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        x xVar = this.f23136b;
        ((z) xVar).f23137b.onPublish(((com.vivo.push.l) xVar).f23178a, this.f23135a.h(), this.f23135a.g());
    }
}
