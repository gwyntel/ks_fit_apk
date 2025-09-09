package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

/* loaded from: classes3.dex */
final class zad extends zag {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f12829a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Activity f12830b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ int f12831c;

    zad(Intent intent, Activity activity, int i2) {
        this.f12829a = intent;
        this.f12830b = activity;
        this.f12831c = i2;
    }

    @Override // com.google.android.gms.common.internal.zag
    public final void zaa() {
        Intent intent = this.f12829a;
        if (intent != null) {
            this.f12830b.startActivityForResult(intent, this.f12831c);
        }
    }
}
