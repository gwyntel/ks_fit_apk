package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class dz implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ long f23639a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f299a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f300a;

    dz(Context context, long j2, boolean z2) {
        this.f299a = context;
        this.f23639a = j2;
        this.f300a = z2;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            dy.i(this.f299a, this.f23639a, this.f300a);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("PowerStatsSP onSendMsg exception: " + e2.getMessage());
        }
    }
}
