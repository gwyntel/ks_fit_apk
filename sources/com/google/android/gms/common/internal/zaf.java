package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.LifecycleFragment;

/* loaded from: classes3.dex */
final class zaf extends zag {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Intent f12835a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ LifecycleFragment f12836b;

    zaf(Intent intent, LifecycleFragment lifecycleFragment, int i2) {
        this.f12835a = intent;
        this.f12836b = lifecycleFragment;
    }

    @Override // com.google.android.gms.common.internal.zag
    public final void zaa() {
        Intent intent = this.f12835a;
        if (intent != null) {
            this.f12836b.startActivityForResult(intent, 2);
        }
    }
}
