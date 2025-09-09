package com.google.android.gms.common.api.internal;

import android.app.Dialog;

/* loaded from: classes3.dex */
final class zan extends zabw {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Dialog f12769a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ zao f12770b;

    zan(zao zaoVar, Dialog dialog) {
        this.f12770b = zaoVar;
        this.f12769a = dialog;
    }

    @Override // com.google.android.gms.common.api.internal.zabw
    public final void zaa() {
        this.f12770b.f12771a.zad();
        if (this.f12769a.isShowing()) {
            this.f12769a.dismiss();
        }
    }
}
