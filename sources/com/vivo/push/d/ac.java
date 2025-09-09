package com.vivo.push.d;

import java.util.List;

/* loaded from: classes4.dex */
final class ac implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23100a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ List f23101b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ List f23102c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ String f23103d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ aa f23104e;

    ac(aa aaVar, int i2, List list, List list2, String str) {
        this.f23104e = aaVar;
        this.f23100a = i2;
        this.f23101b = list;
        this.f23102c = list2;
        this.f23103d = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        aa aaVar = this.f23104e;
        ((z) aaVar).f23137b.onSetAlias(((com.vivo.push.l) aaVar).f23178a, this.f23100a, this.f23101b, this.f23102c, this.f23103d);
    }
}
