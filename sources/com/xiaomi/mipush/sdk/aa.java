package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient;
import com.xiaomi.push.ir;

/* loaded from: classes4.dex */
class aa implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MiTinyDataClient.a.C0192a f23364a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ir f113a;

    aa(MiTinyDataClient.a.C0192a c0192a, ir irVar) {
        this.f23364a = c0192a;
        this.f113a = irVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f23364a.f108a.add(this.f113a);
        this.f23364a.a();
    }
}
