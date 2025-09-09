package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.hm;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ad extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24451a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f991a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ad(int i2, XMPushService xMPushService, jj jjVar) {
        super(i2);
        this.f991a = xMPushService;
        this.f24451a = jjVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            jj jjVarA = z.a((Context) this.f991a, this.f24451a);
            jjVarA.m593a().a("miui_message_unrecognized", "1");
            ai.a(this.f991a, jjVarA);
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f991a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send ack message for unrecognized new miui message.";
    }
}
