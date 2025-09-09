package com.google.android.gms.dynamic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/* loaded from: classes3.dex */
final class zad implements zah {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ FrameLayout f12918a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ LayoutInflater f12919b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ ViewGroup f12920c;

    /* renamed from: d, reason: collision with root package name */
    final /* synthetic */ Bundle f12921d;

    /* renamed from: e, reason: collision with root package name */
    final /* synthetic */ DeferredLifecycleHelper f12922e;

    zad(DeferredLifecycleHelper deferredLifecycleHelper, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f12922e = deferredLifecycleHelper;
        this.f12918a = frameLayout;
        this.f12919b = layoutInflater;
        this.f12920c = viewGroup;
        this.f12921d = bundle;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final int zaa() {
        return 2;
    }

    @Override // com.google.android.gms.dynamic.zah
    public final void zab(LifecycleDelegate lifecycleDelegate) {
        this.f12918a.removeAllViews();
        this.f12918a.addView(this.f12922e.zaa.onCreateView(this.f12919b, this.f12920c, this.f12921d));
    }
}
