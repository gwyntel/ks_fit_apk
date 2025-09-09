package com.xiaomi.push.service.receivers;

import android.content.Context;

/* loaded from: classes4.dex */
class a implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f24619a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ NetworkStatusReceiver f1108a;

    a(NetworkStatusReceiver networkStatusReceiver, Context context) {
        this.f1108a = networkStatusReceiver;
        this.f24619a = context;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f1108a.a(this.f24619a);
    }
}
