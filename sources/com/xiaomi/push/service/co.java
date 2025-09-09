package com.xiaomi.push.service;

/* loaded from: classes4.dex */
class co implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24584a;

    co(XMPushService xMPushService) {
        this.f24584a = xMPushService;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f24584a.f971a = true;
        try {
            com.xiaomi.channel.commonutils.logger.b.m91a("try to trigger the wifi digest broadcast.");
            Object systemService = this.f24584a.getApplicationContext().getSystemService("MiuiWifiService");
            if (systemService != null) {
                com.xiaomi.push.bk.b(systemService, "sendCurrentWifiDigestInfo", new Object[0]);
            }
        } catch (Throwable unused) {
        }
    }
}
