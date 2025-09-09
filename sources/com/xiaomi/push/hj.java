package com.xiaomi.push;

import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class hj extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f23884a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ hi f538a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ long f23885b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    hj(hi hiVar, int i2, long j2, long j3) {
        super(i2);
        this.f538a = hiVar;
        this.f23884a = j2;
        this.f23885b = j3;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        Thread.yield();
        if (!this.f538a.m473c() || this.f538a.a(this.f23884a)) {
            return;
        }
        com.xiaomi.push.service.p.a(this.f538a.f23877b).m788b();
        this.f538a.f23877b.a(22, (Exception) null);
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "check the ping-pong." + this.f23885b;
    }
}
