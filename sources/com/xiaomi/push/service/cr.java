package com.xiaomi.push.service;

import com.xiaomi.push.ex;
import com.xiaomi.push.hc;
import com.xiaomi.push.hf;
import java.util.Map;

/* loaded from: classes4.dex */
class cr extends hc {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24587a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    cr(XMPushService xMPushService, Map map, int i2, String str, hf hfVar) {
        super(map, i2, str, hfVar);
        this.f24587a = xMPushService;
    }

    @Override // com.xiaomi.push.hc
    /* renamed from: a */
    public byte[] mo476a() {
        try {
            ex.b bVar = new ex.b();
            bVar.a(bw.a().m770a());
            return bVar.m303a();
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("getOBBString err: " + e2.toString());
            return null;
        }
    }
}
