package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* loaded from: classes3.dex */
final class zzll implements Runnable {
    private final /* synthetic */ ComponentName zza;
    private final /* synthetic */ zzlj zzb;

    zzll(zzlj zzljVar, ComponentName componentName) {
        this.zzb = zzljVar;
        this.zza = componentName;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkq.j(this.zzb.f13301a, this.zza);
    }
}
