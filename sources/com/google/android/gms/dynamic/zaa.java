package com.google.android.gms.dynamic;

import java.util.Iterator;

/* loaded from: classes3.dex */
final class zaa implements OnDelegateCreatedListener {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ DeferredLifecycleHelper f12911a;

    zaa(DeferredLifecycleHelper deferredLifecycleHelper) {
        this.f12911a = deferredLifecycleHelper;
    }

    @Override // com.google.android.gms.dynamic.OnDelegateCreatedListener
    public final void onDelegateCreated(LifecycleDelegate lifecycleDelegate) {
        this.f12911a.zaa = lifecycleDelegate;
        Iterator it = this.f12911a.zac.iterator();
        while (it.hasNext()) {
            ((zah) it.next()).zab(this.f12911a.zaa);
        }
        this.f12911a.zac.clear();
        this.f12911a.zab = null;
    }
}
