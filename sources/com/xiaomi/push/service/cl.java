package com.xiaomi.push.service;

import com.xiaomi.push.hm;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class cl extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24581a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1079a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ byte[] f1080a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    cl(XMPushService xMPushService, int i2, String str, byte[] bArr) {
        super(i2);
        this.f24581a = xMPushService;
        this.f1079a = str;
        this.f1080a = bArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        try {
            ai.a(this.f24581a, this.f1079a, this.f1080a);
        } catch (hm e2) {
            com.xiaomi.channel.commonutils.logger.b.a(e2);
            this.f24581a.a(10, e2);
        }
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "send mi push message";
    }
}
