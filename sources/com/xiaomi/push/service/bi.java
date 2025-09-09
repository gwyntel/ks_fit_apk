package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bf;

/* loaded from: classes4.dex */
class bi extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bf.b.c f24516a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bi(bf.b.c cVar, int i2) {
        super(i2);
        this.f24516a = cVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        bf bfVarA = bf.a();
        bf.b bVar = this.f24516a.f1047a;
        if (bfVarA.a(bVar.f24503g, bVar.f1043b).f1034a == null) {
            XMPushService xMPushService = bf.b.this.f1036a;
            bf.b bVar2 = this.f24516a.f1047a;
            xMPushService.a(bVar2.f24503g, bVar2.f1043b, 2, null, null);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "check peer job";
    }
}
