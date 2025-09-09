package com.vivo.push.ups;

import com.vivo.push.IPushActionListener;

/* loaded from: classes4.dex */
final class b implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSRegisterCallback f23215a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ VUpsManager f23216b;

    b(VUpsManager vUpsManager, UPSRegisterCallback uPSRegisterCallback) {
        this.f23216b = vUpsManager;
        this.f23215a = uPSRegisterCallback;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f23215a.onResult(new TokenResult(i2, ""));
    }
}
