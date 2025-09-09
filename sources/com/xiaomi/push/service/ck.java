package com.xiaomi.push.service;

import android.content.Context;
import com.aliyun.alink.linksdk.alcs.lpbs.api.AlcsPalConst;
import com.xiaomi.push.hc;
import com.xiaomi.push.service.XMPushService;

/* loaded from: classes4.dex */
class ck extends XMPushService.j {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24579a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1077a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ byte[] f1078a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f24580b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    ck(XMPushService xMPushService, int i2, int i3, String str, byte[] bArr) {
        super(i2);
        this.f24579a = xMPushService;
        this.f24580b = i3;
        this.f1077a = str;
        this.f1078a = bArr;
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    /* renamed from: a */
    public void mo433a() {
        v.m804a((Context) this.f24579a);
        bf.a().m758a(AlcsPalConst.MODEL_TYPE_TGMESH);
        com.xiaomi.push.aa.a(this.f24580b);
        this.f24579a.f956a.c(hc.a());
        com.xiaomi.channel.commonutils.logger.b.m91a("clear account and start registration. " + this.f1077a);
        this.f24579a.a(this.f1078a, this.f1077a);
    }

    @Override // com.xiaomi.push.service.XMPushService.j
    public String a() {
        return "clear account cache.";
    }
}
