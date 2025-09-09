package com.xiaomi.push;

import android.content.Context;

/* loaded from: classes4.dex */
class fa implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ int f23741a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Context f410a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ String f411a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f23742b;

    fa(Context context, String str, int i2, String str2) {
        this.f410a = context;
        this.f411a = str;
        this.f23741a = i2;
        this.f23742b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        ez.c(this.f410a, this.f411a, this.f23741a, this.f23742b);
    }
}
