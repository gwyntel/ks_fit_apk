package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class ea implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f23640a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f301a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f302a;

    ea(Context context, long j2, boolean z2) {
        this.f301a = context;
        this.f23640a = j2;
        this.f302a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            dy.j(this.f301a, this.f23640a, this.f302a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("PowerStatsSP onReceiveMsg exception: " + e2.getMessage());
        }
    }
}
