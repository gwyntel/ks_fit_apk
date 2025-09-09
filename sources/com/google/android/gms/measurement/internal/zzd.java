package com.google.android.gms.measurement.internal;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzd implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ zzb zzc;

    zzd(zzb zzbVar, String str, long j2) {
        this.zzc = zzbVar;
        this.zza = str;
        this.zzb = j2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzb.c(this.zzc, this.zza, this.zzb);
    }
}
