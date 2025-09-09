package com.xiaomi.push;

import android.app.NotificationChannel;
import android.content.Context;
import java.util.concurrent.Callable;

/* loaded from: classes4.dex */
class bx implements Callable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ NotificationChannel f23520a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f221a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ bv f222a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f223a;

    bx(bv bvVar, Context context, String str, NotificationChannel notificationChannel) {
        this.f222a = bvVar;
        this.f221a = context;
        this.f223a = str;
        this.f23520a = notificationChannel;
    }

    @Override // java.util.concurrent.Callable
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public String call() {
        return String.valueOf(jx.a(this.f221a, this.f223a, this.f23520a));
    }
}
