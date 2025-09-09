package com.google.android.gms.common.api.internal;

import android.os.Bundle;

/* loaded from: classes3.dex */
final class zza implements Runnable {

    /* renamed from: a, reason: collision with root package name */
    final /* synthetic */ LifecycleCallback f12779a;

    /* renamed from: b, reason: collision with root package name */
    final /* synthetic */ String f12780b;

    /* renamed from: c, reason: collision with root package name */
    final /* synthetic */ zzb f12781c;

    zza(zzb zzbVar, LifecycleCallback lifecycleCallback, String str) {
        this.f12781c = zzbVar;
        this.f12779a = lifecycleCallback;
        this.f12780b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundle;
        zzb zzbVar = this.f12781c;
        if (zzbVar.zzc > 0) {
            LifecycleCallback lifecycleCallback = this.f12779a;
            if (zzbVar.zzd != null) {
                bundle = zzbVar.zzd.getBundle(this.f12780b);
            } else {
                bundle = null;
            }
            lifecycleCallback.onCreate(bundle);
        }
        if (this.f12781c.zzc >= 2) {
            this.f12779a.onStart();
        }
        if (this.f12781c.zzc >= 3) {
            this.f12779a.onResume();
        }
        if (this.f12781c.zzc >= 4) {
            this.f12779a.onStop();
        }
        if (this.f12781c.zzc >= 5) {
            this.f12779a.onDestroy();
        }
    }
}
