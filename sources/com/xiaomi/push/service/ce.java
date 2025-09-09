package com.xiaomi.push.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Context;
import com.xiaomi.push.ah;

/* loaded from: classes4.dex */
class ce extends ah.a {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f24573a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Notification f1072a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f1073a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f1074a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f24574b;

    ce(int i2, String str, Context context, String str2, Notification notification) {
        this.f24573a = i2;
        this.f1074a = str;
        this.f1073a = context;
        this.f24574b = str2;
        this.f1072a = notification;
    }

    @Override // com.xiaomi.push.ah.a
    /* renamed from: a */
    public String mo224a() {
        return cd.b(this.f24573a, this.f1074a);
    }

    @Override // java.lang.Runnable
    @TargetApi(19)
    public void run() {
        cd.c(this.f1073a, this.f24574b, this.f24573a, this.f1074a, this.f1072a);
    }
}
