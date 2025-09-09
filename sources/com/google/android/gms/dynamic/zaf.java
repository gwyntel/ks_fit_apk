package com.google.android.gms.dynamic;

/* loaded from: classes3.dex */
final class zaf implements zah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DeferredLifecycleHelper f12925a;

    zaf(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.f12925a = deferredLifecycleHelper;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final int zaa() {
        return 4;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final void zab(LifecycleDelegate lifecycleDelegate) {
        this.f12925a.zaa.onStart();
    }
}
