package com.xiaomi.mipush.sdk;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes4.dex */
class al implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f23377a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ Intent f118a;

    al(Context context, Intent intent) {
        this.f23377a = context;
        this.f118a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        PushMessageHandler.b(this.f23377a, this.f118a);
    }
}
