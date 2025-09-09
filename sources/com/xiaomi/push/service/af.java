package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.hm;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class af extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24453a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f994a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f995a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24454b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    af(int i2, XMPushService xMPushService, jj jjVar, String str, String str2) {
        super(i2);
        this.f994a = xMPushService;
        this.f24453a = jjVar;
        this.f995a = str;
        this.f24454b = str2;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            jj jjVarA = z.a((Context) this.f994a, this.f24453a);
            jjVarA.f742a.a("error", this.f995a);
            jjVarA.f742a.a("reason", this.f24454b);
            ai.a(this.f994a, jjVarA);
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f994a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send wrong message ack for message.";
    }
}
