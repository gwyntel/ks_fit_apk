package com.xiaomi.push;

import com.xiaomi.push.ak;
import com.xiaomi.push.ef;

/* loaded from: classes4.dex */
class eh extends ak.b {

    /* renamed from: a, reason: collision with root package name */
    ak.b f23655a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ef f325a;

    eh(ef efVar) {
        this.f325a = efVar;
    }

    @Override // com.xiaomi.push.ak.b
    public void b() {
        ef.b bVar = (ef.b) this.f325a.f313a.peek();
        if (bVar == null || !bVar.a()) {
            return;
        }
        if (this.f325a.f313a.remove(bVar)) {
            this.f23655a = bVar;
        }
        ak.b bVar2 = this.f23655a;
        if (bVar2 != null) {
            bVar2.b();
        }
    }

    @Override // com.xiaomi.push.ak.b
    /* renamed from: c */
    public void mo308c() {
        ak.b bVar = this.f23655a;
        if (bVar != null) {
            bVar.mo308c();
        }
    }
}
