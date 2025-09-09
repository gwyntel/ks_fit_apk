package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzss;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzjw implements Runnable {
    private final /* synthetic */ zzie zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ long zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ zzie zze;
    private final /* synthetic */ zzin zzf;

    zzjw(zzin zzinVar, zzie zzieVar, long j2, long j3, boolean z2, zzie zzieVar2) {
        this.zzf = zzinVar;
        this.zza = zzieVar;
        this.zzb = j2;
        this.zzc = j3;
        this.zzd = z2;
        this.zze = zzieVar2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.zzf.f(this.zza);
        this.zzf.c(this.zzb, false);
        zzin.g(this.zzf, this.zza, this.zzc, true, this.zzd);
        if (zzss.zzb() && this.zzf.zze().zza(zzbi.zzbs)) {
            zzin.h(this.zzf, this.zza, this.zze);
        }
    }
}
