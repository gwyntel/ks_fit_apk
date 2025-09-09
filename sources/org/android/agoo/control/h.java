package org.android.agoo.control;

import android.content.Intent;

/* loaded from: classes5.dex */
class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f26535a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ g f26536b;

    h(g gVar, Intent intent) {
        this.f26536b = gVar;
        this.f26535a = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f26536b.f26534a.onHandleIntent(this.f26535a);
    }
}
