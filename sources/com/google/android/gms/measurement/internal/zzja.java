package com.google.android.gms.measurement.internal;

import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
final class zzja implements Executor {
    private final /* synthetic */ zzin zza;

    zzja(zzin zzinVar) {
        this.zza = zzinVar;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) throws IllegalStateException {
        this.zza.zzl().zzb(runnable);
    }
}
