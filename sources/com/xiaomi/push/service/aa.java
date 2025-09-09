package com.xiaomi.push.service;

import com.xiaomi.push.hm;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class aa extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24448a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f988a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    aa(int i2, XMPushService xMPushService, jj jjVar) {
        super(i2);
        this.f988a = xMPushService;
        this.f24448a = jjVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            ai.a(this.f988a, ai.a(this.f24448a.b(), this.f24448a.m594a()));
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f988a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send app absent message.";
    }
}
