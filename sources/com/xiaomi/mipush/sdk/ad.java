package com.xiaomi.mipush.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* loaded from: classes4.dex */
class ad extends BroadcastReceiver {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NotificationClickedActivity f23367a;

    ad(NotificationClickedActivity notificationClickedActivity) {
        this.f23367a = notificationClickedActivity;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        com.xiaomi.channel.commonutils.logger.b.b("clicked activity finish by normal.");
        this.f23367a.finish();
    }
}
