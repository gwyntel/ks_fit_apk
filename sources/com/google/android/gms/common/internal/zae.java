package com.google.android.gms.common.internal;

import android.content.Intent;
import androidx.fragment.app.Fragment;

/* loaded from: classes3.dex */
final class zae extends zag {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f12832a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Fragment f12833b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f12834c;

    zae(Intent intent, Fragment fragment, int i2) {
        this.f12832a = intent;
        this.f12833b = fragment;
        this.f12834c = i2;
    }

    @Override // com.google.android.gms.common.internal.zag
    public final void zaa() {
        Intent intent = this.f12832a;
        if (intent != null) {
            this.f12833b.startActivityForResult(intent, this.f12834c);
        }
    }
}
