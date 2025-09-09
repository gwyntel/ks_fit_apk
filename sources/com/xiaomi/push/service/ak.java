package com.xiaomi.push.service;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.push.service.bf;

/* loaded from: classes4.dex */
class ak implements bf.b.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24458a;

    ak(XMPushService xMPushService) {
        this.f24458a = xMPushService;
    }

    @Override // com.xiaomi.push.service.bf.b.a
    public void a(bf.c cVar, bf.c cVar2, int i2) throws InterruptedException {
        if (cVar2 == bf.c.binded) {
            y.a(this.f24458a, true);
            y.a(this.f24458a);
        } else if (cVar2 == bf.c.unbind) {
            com.xiaomi.channel.commonutils.logger.b.m91a("onChange unbind");
            y.a(this.f24458a, ErrorCode.ERROR_SERVICE_UNAVAILABLE, " the push is not connected.");
        }
    }
}
