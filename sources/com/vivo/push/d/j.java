package com.vivo.push.d;

import java.util.List;

/* loaded from: classes4.dex */
final class j implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23115a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f23116b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f23117c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f23118d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ h f23119e;

    j(h hVar, int i2, List list, List list2, String str) {
        this.f23119e = hVar;
        this.f23115a = i2;
        this.f23116b = list;
        this.f23117c = list2;
        this.f23118d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        h hVar = this.f23119e;
        ((z) hVar).f23137b.onDelAlias(((com.vivo.push.l) hVar).f23178a, this.f23115a, this.f23116b, this.f23117c, this.f23118d);
    }
}
