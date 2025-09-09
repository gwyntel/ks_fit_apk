package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ct implements XMPushService.n {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24589a;

    ct(XMPushService xMPushService) {
        this.f24589a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.n
    /* renamed from: a */
    public void mo511a() {
        com.xiaomi.push.bv.a(this.f24589a.getApplicationContext());
    }
}
