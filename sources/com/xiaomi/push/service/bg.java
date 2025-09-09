package com.xiaomi.push.service;

import com.xiaomi.push.service.bf;

/* loaded from: classes4.dex */
class bg implements bf.b.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bf.b f24514a;

    bg(bf.b bVar) {
        this.f24514a = bVar;
    }

    @Override // com.xiaomi.push.service.bf.b.a
    public void a(bf.c cVar, bf.c cVar2, int i2) {
        if (cVar2 == bf.c.binding) {
            this.f24514a.f1036a.a(this.f24514a.f1035a, 60000L);
        } else {
            this.f24514a.f1036a.b(this.f24514a.f1035a);
        }
    }
}
