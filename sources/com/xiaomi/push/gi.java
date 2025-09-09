package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class gi extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ gh f23829a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    gi(gh ghVar, int i2) {
        super(i2);
        this.f23829a = ghVar;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a, reason: collision with other method in class */
    public void mo433a() {
        this.f23829a.c();
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "Handling bind stats";
    }
}
