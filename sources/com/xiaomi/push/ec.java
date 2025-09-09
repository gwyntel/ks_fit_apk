package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class ec implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f23642a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f305a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f306a;

    ec(Context context, long j2, boolean z2) {
        this.f305a = context;
        this.f23642a = j2;
        this.f306a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            dy.l(this.f305a, this.f23642a, this.f306a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("PowerStatsSP onPong exception: " + e2.getMessage());
        }
    }
}
