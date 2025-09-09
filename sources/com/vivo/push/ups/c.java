package com.vivo.push.ups;

import com.vivo.push.IPushActionListener;

/* loaded from: classes4.dex */
final class c implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSTurnCallback f23217a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ VUpsManager f23218b;

    c(VUpsManager vUpsManager, UPSTurnCallback uPSTurnCallback) {
        this.f23218b = vUpsManager;
        this.f23217a = uPSTurnCallback;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f23217a.onResult(new CodeResult(i2));
    }
}
