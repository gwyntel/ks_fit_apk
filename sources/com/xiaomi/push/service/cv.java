package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class cv extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24591a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    cv(XMPushService xMPushService, int i2) {
        super(i2);
        this.f24591a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        ai.a(this.f24591a);
        if (com.xiaomi.push.bg.b(this.f24591a)) {
            this.f24591a.a(true);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "prepare the mi push account.";
    }
}
