package com.xiaomi.push.service;

import android.database.ContentObserver;
import android.os.Handler;
import com.xiaomi.push.service.XMPushService.g;

/* loaded from: classes4.dex */
class cu extends ContentObserver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24590a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    cu(XMPushService xMPushService, Handler handler) {
        super(handler);
        this.f24590a = xMPushService;
    }

    @Override // android.database.ContentObserver
    public void onChange(boolean z2) {
        super.onChange(z2);
        boolean zM704g = this.f24590a.m704g();
        com.xiaomi.channel.commonutils.logger.b.m91a("SuperPowerMode:" + zM704g);
        this.f24590a.e();
        if (!zM704g) {
            this.f24590a.a(true);
        } else {
            XMPushService xMPushService = this.f24590a;
            xMPushService.a(xMPushService.new g(24, null));
        }
    }
}
