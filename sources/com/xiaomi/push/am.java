package com.xiaomi.push;

import com.xiaomi.push.ak;

/* loaded from: classes4.dex */
class am implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ ak.b f23441a;

    /* renamed from: a, reason: collision with other field name */
    final /* synthetic */ ak f174a;

    am(ak akVar, ak.b bVar) {
        this.f174a = akVar;
        this.f23441a = bVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f174a.a(this.f23441a);
    }
}
