package com.taobao.accs.data;

import android.content.Context;
import android.content.Intent;

/* loaded from: classes4.dex */
class h implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Context f20158a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Intent f20159b;

    h(Context context, Intent intent) {
        this.f20158a = context;
        this.f20159b = intent;
    }

    @Override // java.lang.Runnable
    public void run() {
        g.a().b(this.f20158a, this.f20159b);
    }
}
