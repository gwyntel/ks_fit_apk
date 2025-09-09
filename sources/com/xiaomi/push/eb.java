package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class eb implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f23641a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f303a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f304a;

    eb(Context context, long j2, boolean z2) {
        this.f303a = context;
        this.f23641a = j2;
        this.f304a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            dy.k(this.f303a, this.f23641a, this.f304a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("PowerStatsSP onPing exception: " + e2.getMessage());
        }
    }
}
