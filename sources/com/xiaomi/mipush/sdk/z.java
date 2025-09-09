package com.xiaomi.mipush.sdk;

import android.content.Context;

/* loaded from: classes4.dex */
class z implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23428a;

    z(Context context) {
        this.f23428a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        MessageHandleService.c(this.f23428a);
    }
}
