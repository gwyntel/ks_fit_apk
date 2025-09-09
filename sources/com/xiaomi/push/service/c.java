package com.xiaomi.push.service;

import com.xiaomi.push.gq;
import com.xiaomi.push.hm;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class c extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    private XMPushService f24569a;

    /* renamed from: a, reason: collision with other field name */
    private gq[] f1068a;

    public c(XMPushService xMPushService, gq[] gqVarArr) {
        super(4);
        this.f24569a = xMPushService;
        this.f1068a = gqVarArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            gq[] gqVarArr = this.f1068a;
            if (gqVarArr != null) {
                this.f24569a.a(gqVarArr);
            }
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f24569a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "batch send message.";
    }
}
