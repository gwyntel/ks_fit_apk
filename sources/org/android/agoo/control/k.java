package org.android.agoo.control;

import android.content.Intent;

/* loaded from: classes5.dex */
class k implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f26539a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ BaseIntentService f26540b;

    k(BaseIntentService baseIntentService, Intent intent) {
        this.f26540b = baseIntentService;
        this.f26539a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f26540b.onHandleIntent(this.f26539a);
    }
}
