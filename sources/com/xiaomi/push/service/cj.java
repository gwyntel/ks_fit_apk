package com.xiaomi.push.service;

import android.os.SystemClock;
import com.xiaomi.push.gq;
import com.xiaomi.push.hg;
import com.xiaomi.push.hs;
import com.xiaomi.push.service.XMPushService.d;
import com.xiaomi.push.service.XMPushService.m;

/* loaded from: classes4.dex */
class cj implements hg {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24578a;

    cj(XMPushService xMPushService) {
        this.f24578a = xMPushService;
    }

    @Override // com.xiaomi.push.hg
    public void a(hs hsVar) {
        XMPushService xMPushService = this.f24578a;
        xMPushService.a(xMPushService.new m(hsVar));
    }

    @Override // com.xiaomi.push.hg
    public void a(gq gqVar) {
        if (e.a(gqVar)) {
            bq.a().a(gqVar.e(), SystemClock.elapsedRealtime(), this.f24578a.m707a());
        }
        XMPushService xMPushService = this.f24578a;
        xMPushService.a(xMPushService.new d(gqVar));
    }
}
