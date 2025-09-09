package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService.g;
import com.xiaomi.push.service.bf;

/* loaded from: classes4.dex */
class cs implements bf.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24588a;

    cs(XMPushService xMPushService) {
        this.f24588a = xMPushService;
    }

    @Override // com.xiaomi.push.service.bf.a
    public void a() {
        this.f24588a.e();
        if (bf.a().m753a() <= 0) {
            XMPushService xMPushService = this.f24588a;
            xMPushService.a(xMPushService.new g(12, null));
        }
    }
}
