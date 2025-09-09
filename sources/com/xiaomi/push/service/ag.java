package com.xiaomi.push.service;

import com.xiaomi.push.hm;
import com.xiaomi.push.in;
import com.xiaomi.push.ix;
import com.xiaomi.push.je;
import com.xiaomi.push.jj;
import com.xiaomi.push.jm;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ag extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24455a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ jm f996a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f997a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ag(int i2, jm jmVar, jj jjVar, XMPushService xMPushService) {
        super(i2);
        this.f996a = jmVar;
        this.f24455a = jjVar;
        this.f997a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            je jeVar = new je();
            jeVar.c(ix.CancelPushMessageACK.f620a);
            jeVar.a(this.f996a.m608a());
            jeVar.a(this.f996a.a());
            jeVar.b(this.f996a.b());
            jeVar.e(this.f996a.c());
            jeVar.a(0L);
            jeVar.d("success clear push message.");
            ai.a(this.f997a, ai.b(this.f24455a.b(), this.f24455a.m594a(), jeVar, in.Notification));
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.d("clear push message. " + e2);
            this.f997a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send ack message for clear push message.";
    }
}
