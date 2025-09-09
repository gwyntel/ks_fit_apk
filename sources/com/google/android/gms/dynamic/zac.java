package com.google.android.gms.dynamic;

import android.os.Bundle;

/* loaded from: classes3.dex */
final class zac implements zah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Bundle f12916a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ DeferredLifecycleHelper f12917b;

    zac(DeferredLifecycleHelper deferredLifecycleHelper, Bundle bundle) {
        this.f12917b = deferredLifecycleHelper;
        this.f12916a = bundle;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final int zaa() {
        return 1;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final void zab(LifecycleDelegate lifecycleDelegate) {
        this.f12917b.zaa.onCreate(this.f12916a);
    }
}
