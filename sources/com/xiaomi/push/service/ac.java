package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.hm;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ac extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24450a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f990a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ac(int i2, XMPushService xMPushService, jj jjVar) {
        super(i2);
        this.f990a = xMPushService;
        this.f24450a = jjVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            jj jjVarA = z.a((Context) this.f990a, this.f24450a);
            jjVarA.m593a().a("message_obsleted", "1");
            ai.a(this.f990a, jjVarA);
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f990a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send ack message for obsleted message.";
    }
}
