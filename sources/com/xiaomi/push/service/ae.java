package com.xiaomi.push.service;

import android.content.Context;
import com.xiaomi.push.hm;
import com.xiaomi.push.jj;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ae extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jj f24452a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f992a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f993a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ae(int i2, XMPushService xMPushService, jj jjVar, String str) {
        super(i2);
        this.f992a = xMPushService;
        this.f24452a = jjVar;
        this.f993a = str;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            jj jjVarA = z.a((Context) this.f992a, this.f24452a);
            jjVarA.m593a().a("absent_target_package", this.f993a);
            ai.a(this.f992a, jjVarA);
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f992a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send app absent ack message for message.";
    }
}
