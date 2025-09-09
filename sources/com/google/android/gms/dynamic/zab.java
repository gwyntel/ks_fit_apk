package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;

/* loaded from: classes3.dex */
final class zab implements zah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ Activity f12912a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ Bundle f12913b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ Bundle f12914c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ DeferredLifecycleHelper f12915d;

    zab(DeferredLifecycleHelper deferredLifecycleHelper, Activity activity, Bundle bundle, Bundle bundle2) {
        this.f12915d = deferredLifecycleHelper;
        this.f12912a = activity;
        this.f12913b = bundle;
        this.f12914c = bundle2;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final int zaa() {
        return 0;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final void zab(LifecycleDelegate lifecycleDelegate) {
        this.f12915d.zaa.onInflate(this.f12912a, this.f12913b, this.f12914c);
    }
}
