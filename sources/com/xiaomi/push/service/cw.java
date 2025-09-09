package com.xiaomi.push.service;

import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.v;

/* loaded from: classes4.dex */
class cw implements v.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService.j f24592a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ XMPushService f1081a;

    cw(XMPushService xMPushService, XMPushService.j jVar) {
        this.f1081a = xMPushService;
        this.f24592a = jVar;
    }

    @Override // com.xiaomi.push.service.v.a
    public void a() {
        this.f1081a.a(this.f24592a);
    }
}
