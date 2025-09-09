package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes4.dex */
class y implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23427a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Intent f158a;

    y(Context context, Intent intent) {
        this.f23427a = context;
        this.f158a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            this.f23427a.startService(this.f158a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a(e2.getMessage());
        }
    }
}
