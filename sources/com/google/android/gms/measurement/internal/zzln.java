package com.google.android.gms.measurement.internal;

import android.content.ComponentName;

/* loaded from: classes3.dex */
final class zzln implements Runnable {
    private final /* synthetic */ zzlj zza;

    zzln(zzlj zzljVar) {
        this.zza = zzljVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzkq.j(this.zza.f13301a, new ComponentName(this.zza.f13301a.zza(), "com.google.android.gms.measurement.AppMeasurementService"));
    }
}
