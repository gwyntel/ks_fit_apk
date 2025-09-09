package com.xiaomi.push.service;

import android.content.ComponentName;
import android.content.pm.PackageManager;

/* loaded from: classes4.dex */
class cp implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ XMPushService f24585a;

    cp(XMPushService xMPushService) {
        this.f24585a = xMPushService;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            PackageManager packageManager = this.f24585a.getApplicationContext().getPackageManager();
            ComponentName componentName = new ComponentName(this.f24585a.getApplicationContext(), "com.xiaomi.push.service.receivers.PingReceiver");
            if (packageManager.getComponentEnabledSetting(componentName) != 2) {
                packageManager.setComponentEnabledSetting(componentName, 2, 1);
            }
        } catch (Throwable th) {
            com.xiaomi.channel.commonutils.logger.b.m91a("[Alarm] disable ping receiver may be failure. " + th);
        }
    }
}
