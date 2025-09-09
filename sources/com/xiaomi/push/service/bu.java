package com.xiaomi.push.service;

import com.xiaomi.push.gq;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
public class bu extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    private gq f24562a;

    /* renamed from: a, reason: collision with other field name */
    private XMPushService f1056a;

    public bu(XMPushService xMPushService, gq gqVar) {
        super(4);
        this.f1056a = xMPushService;
        this.f24562a = gqVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            gq gqVar = this.f24562a;
            if (gqVar != null) {
                if (e.a(gqVar)) {
                    this.f24562a.c(System.currentTimeMillis() - this.f24562a.m443a());
                }
                this.f1056a.a(this.f24562a);
            }
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f1056a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send a message.";
    }
}
