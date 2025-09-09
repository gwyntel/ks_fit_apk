package com.vivo.push.ups;

import com.vivo.push.IPushActionListener;

/* loaded from: classes4.dex */
final class d implements IPushActionListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ UPSTurnCallback f23219a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ VUpsManager f23220b;

    d(VUpsManager vUpsManager, UPSTurnCallback uPSTurnCallback) {
        this.f23220b = vUpsManager;
        this.f23219a = uPSTurnCallback;
    }

    @Override // com.vivo.push.IPushActionListener
    public final void onStateChanged(int i2) {
        this.f23219a.onResult(new CodeResult(i2));
    }
}
