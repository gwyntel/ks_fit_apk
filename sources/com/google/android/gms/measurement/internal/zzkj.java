package com.google.android.gms.measurement.internal;

import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzkj implements Runnable {
    private final /* synthetic */ zzkf zza;
    private final /* synthetic */ zzkf zzb;
    private final /* synthetic */ long zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ zzki zze;

    zzkj(zzki zzkiVar, zzkf zzkfVar, zzkf zzkfVar2, long j2, boolean z2) {
        this.zze = zzkiVar;
        this.zza = zzkfVar;
        this.zzb = zzkfVar2;
        this.zzc = j2;
        this.zzd = z2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.zze.zza(this.zza, this.zzb, this.zzc, this.zzd, null);
    }
}
