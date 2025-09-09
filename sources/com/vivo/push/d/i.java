package com.vivo.push.d;

import java.util.List;

/* loaded from: classes4.dex */
final class i implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23110a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f23111b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f23112c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f23113d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ h f23114e;

    i(h hVar, int i2, List list, List list2, String str) {
        this.f23114e = hVar;
        this.f23110a = i2;
        this.f23111b = list;
        this.f23112c = list2;
        this.f23113d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        h hVar = this.f23114e;
        ((z) hVar).f23137b.onDelTags(((com.vivo.push.l) hVar).f23178a, this.f23110a, this.f23111b, this.f23112c, this.f23113d);
    }
}
