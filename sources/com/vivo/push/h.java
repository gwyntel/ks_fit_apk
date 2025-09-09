package com.vivo.push;

/* loaded from: classes4.dex */
final class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.b f23169a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f23170b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ e f23171c;

    h(e eVar, com.vivo.push.b.b bVar, String str) {
        this.f23171c = eVar;
        this.f23169a = bVar;
        this.f23170b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f23171c.a(this.f23169a);
        this.f23171c.e(this.f23170b);
    }
}
