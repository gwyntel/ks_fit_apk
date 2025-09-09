package com.xiaomi.push.service;

import com.xiaomi.push.in;
import com.xiaomi.push.jm;
import com.xiaomi.push.jx;

/* loaded from: classes4.dex */
class ci implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ jm f24577a;

    ci(jm jmVar) {
        this.f24577a = jmVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        byte[] bArrA = jx.a(ai.a(this.f24577a.c(), this.f24577a.b(), this.f24577a, in.Notification));
        if (ch.f24576a instanceof XMPushService) {
            ((XMPushService) ch.f24576a).a(this.f24577a.c(), bArrA, true);
        } else {
            com.xiaomi.channel.commonutils.logger.b.m91a("UNDatas UploadNotificationDatas failed because not xmsf");
        }
    }
}
