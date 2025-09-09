package com.google.android.gms.dynamic;

/* loaded from: classes3.dex */
final class zag implements zah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DeferredLifecycleHelper f12926a;

    zag(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.f12926a = deferredLifecycleHelper;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final int zaa() {
        return 5;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final void zab(LifecycleDelegate lifecycleDelegate) {
        this.f12926a.zaa.onResume();
    }
}
