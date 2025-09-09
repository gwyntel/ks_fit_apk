package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.bf;

/* loaded from: classes4.dex */
class bh extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ bf.b.c f24515a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    bh(bf.b.c cVar, int i2) {
        super(i2);
        this.f24515a = cVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        bf.b.c cVar = this.f24515a;
        if (cVar.f24509a == cVar.f1047a.f1034a) {
            com.xiaomi.channel.commonutils.logger.b.b("clean peer, chid = " + this.f24515a.f1047a.f24503g);
            this.f24515a.f1047a.f1034a = null;
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "clear peer job";
    }
}
