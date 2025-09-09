package com.xiaomi.mipush.sdk;

/* loaded from: classes4.dex */
class ac implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NotificationClickedActivity f23366a;

    ac(NotificationClickedActivity notificationClickedActivity) {
        this.f23366a = notificationClickedActivity;
    }

    @Override // java.lang.Runnable
    public void run() {
        com.xiaomi.channel.commonutils.logger.b.e("clicked activity finish by timeout.");
        this.f23366a.finish();
    }
}
