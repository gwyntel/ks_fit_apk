package com.xiaomi.mipush.sdk;

import com.xiaomi.mipush.sdk.MiTinyDataClient;

/* loaded from: classes4.dex */
class ab implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ MiTinyDataClient.a.C0192a f23365a;

    ab(MiTinyDataClient.a.C0192a c0192a) {
        this.f23365a = c0192a;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.f23365a.f108a.size() != 0) {
            this.f23365a.b();
        } else if (this.f23365a.f109a != null) {
            this.f23365a.f109a.cancel(false);
            this.f23365a.f109a = null;
        }
    }
}
