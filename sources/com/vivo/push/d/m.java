package com.vivo.push.d;

/* loaded from: classes4.dex */
final class m implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.m f23120a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ l f23121b;

    m(l lVar, com.vivo.push.b.m mVar) {
        this.f23121b = lVar;
        this.f23120a = mVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        l lVar = this.f23121b;
        ((z) lVar).f23137b.onListTags(((com.vivo.push.l) lVar).f23178a, this.f23120a.h(), this.f23120a.d(), this.f23120a.g());
    }
}
