package com.google.android.gms.measurement.internal;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzju implements Runnable {
    private final /* synthetic */ Boolean zza;
    private final /* synthetic */ zzin zzb;

    zzju(zzin zzinVar, Boolean bool) {
        this.zzb = zzinVar;
        this.zza = bool;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.zzb.zza(this.zza, true);
    }
}
