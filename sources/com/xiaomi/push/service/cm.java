package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class cm extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24582a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    cm(XMPushService xMPushService, int i2) {
        super(i2);
        this.f24582a = xMPushService;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        if (this.f24582a.f955a != null) {
            this.f24582a.f955a.b(15, (Exception) null);
            this.f24582a.f955a = null;
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "disconnect for service destroy.";
    }
}
