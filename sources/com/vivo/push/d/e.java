package com.vivo.push.d;

import android.text.TextUtils;

/* loaded from: classes4.dex */
final class e implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ String f23107a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ com.vivo.push.b.i f23108b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ d f23109c;

    e(d dVar, String str, com.vivo.push.b.i iVar) {
        this.f23109c = dVar;
        this.f23107a = str;
        this.f23108b = iVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (!TextUtils.isEmpty(this.f23107a)) {
            d dVar = this.f23109c;
            ((z) dVar).f23137b.onReceiveRegId(((com.vivo.push.l) dVar).f23178a, this.f23107a);
        }
        d dVar2 = this.f23109c;
        ((z) dVar2).f23137b.onBind(((com.vivo.push.l) dVar2).f23178a, this.f23108b.h(), this.f23108b.d());
    }
}
