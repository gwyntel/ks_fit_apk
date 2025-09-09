package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.hm;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;
import java.util.Map;

/* loaded from: classes4.dex */
class ab extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24449a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f989a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ab(int i2, XMPushService xMPushService, jj jjVar) {
        super(i2);
        this.f989a = xMPushService;
        this.f24449a = jjVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        Map<String, String> mapA;
        try {
            if (com.xiaomi.push.j.m550a((Context) this.f989a)) {
                try {
                    mapA = ah.a((Context) this.f989a, this.f24449a);
                } catch (Throwable unused) {
                }
            } else {
                mapA = null;
            }
            ai.a(this.f989a, z.a(this.f989a, this.f24449a, mapA));
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f989a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send ack message for message.";
    }
}
