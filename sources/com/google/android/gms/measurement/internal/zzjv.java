package com.google.android.gms.measurement.internal;

import com.google.android.gms.internal.measurement.zzss;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzjv implements Runnable {
    private final /* synthetic */ zzie zza;
    private final /* synthetic */ long zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzie zzd;
    private final /* synthetic */ zzin zze;

    zzjv(zzin zzinVar, zzie zzieVar, long j2, boolean z2, zzie zzieVar2) {
        this.zze = zzinVar;
        this.zza = zzieVar;
        this.zzb = j2;
        this.zzc = z2;
        this.zzd = zzieVar2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.zze.f(this.zza);
        zzin.g(this.zze, this.zza, this.zzb, false, this.zzc);
        if (zzss.zzb() && this.zze.zze().zza(zzbi.zzbs)) {
            zzin.h(this.zze, this.zza, this.zzd);
        }
    }
}
