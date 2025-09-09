package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class hk extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ hi f23886a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Exception f539a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f23887b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    hk(hi hiVar, int i2, int i3, Exception exc) {
        super(i2);
        this.f23886a = hiVar;
        this.f23887b = i3;
        this.f539a = exc;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        this.f23886a.f23877b.a(this.f23887b, this.f539a);
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "shutdown the connection. " + this.f23887b + ", " + this.f539a;
    }
}
