package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class dq implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23605a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ long f286a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f287a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f288a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ boolean f289a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ int f23606b;

    /* renamed from: b, reason: collision with other field name */
    final /* synthetic */ long f290b;

    /* renamed from: b, reason: collision with other field name */
    final /* synthetic */ String f291b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f23607c;

    dq(Context context, String str, boolean z2, long j2, int i2, long j3, int i3, String str2, int i4) {
        this.f287a = context;
        this.f288a = str;
        this.f289a = z2;
        this.f286a = j2;
        this.f23605a = i2;
        this.f290b = j3;
        this.f23606b = i3;
        this.f291b = str2;
        this.f23607c = i4;
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            dp.c(this.f287a, this.f288a, this.f289a, this.f286a, this.f23605a, this.f290b, this.f23606b, this.f291b, this.f23607c);
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m91a("DisconnectStatsSP onDisconnection exception: " + e2.getMessage());
        }
    }
}
