package com.vivo.push.d;

import java.util.List;

/* loaded from: classes4.dex */
final class ab implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23095a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f23096b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f23097c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f23098d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ aa f23099e;

    ab(aa aaVar, int i2, List list, List list2, String str) {
        this.f23099e = aaVar;
        this.f23095a = i2;
        this.f23096b = list;
        this.f23097c = list2;
        this.f23098d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        aa aaVar = this.f23099e;
        ((z) aaVar).f23137b.onSetTags(((com.vivo.push.l) aaVar).f23178a, this.f23095a, this.f23096b, this.f23097c, this.f23098d);
    }
}
