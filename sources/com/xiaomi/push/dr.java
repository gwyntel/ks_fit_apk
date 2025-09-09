package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class dr implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f23608a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f292a;

    dr(Context context, long j2) {
        this.f292a = context;
        this.f23608a = j2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            dp.c(this.f292a, this.f23608a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("DisconnectStatsSP onReconnection exception: " + e2.getMessage());
        }
    }
}
