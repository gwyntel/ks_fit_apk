package com.vivo.push;

/* loaded from: classes4.dex */
final class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.b f23173a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f23174b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ e f23175c;

    j(e eVar, com.vivo.push.b.b bVar, String str) {
        this.f23175c = eVar;
        this.f23173a = bVar;
        this.f23174b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f23175c.a(this.f23173a);
        this.f23175c.e(this.f23174b);
    }
}
