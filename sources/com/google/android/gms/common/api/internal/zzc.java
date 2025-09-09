package com.google.android.gms.common.api.internal;

import android.os.Bundle;

/* loaded from: classes3.dex */
final class zzc implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ LifecycleCallback f12782a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f12783b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzd f12784c;

    zzc(zzd zzdVar, LifecycleCallback lifecycleCallback, String str) {
        this.f12784c = zzdVar;
        this.f12782a = lifecycleCallback;
        this.f12783b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundle;
        zzd zzdVar = this.f12784c;
        if (zzdVar.zzc > 0) {
            LifecycleCallback lifecycleCallback = this.f12782a;
            if (zzdVar.zzd != null) {
                bundle = zzdVar.zzd.getBundle(this.f12783b);
            } else {
                bundle = null;
            }
            lifecycleCallback.onCreate(bundle);
        }
        if (this.f12784c.zzc >= 2) {
            this.f12782a.onStart();
        }
        if (this.f12784c.zzc >= 3) {
            this.f12782a.onResume();
        }
        if (this.f12784c.zzc >= 4) {
            this.f12782a.onStop();
        }
        if (this.f12784c.zzc >= 5) {
            this.f12782a.onDestroy();
        }
    }
}
