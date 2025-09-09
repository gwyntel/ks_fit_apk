package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
final class zzkk implements Runnable {
    private final /* synthetic */ Bundle zza;
    private final /* synthetic */ zzkf zzb;
    private final /* synthetic */ zzkf zzc;
    private final /* synthetic */ long zzd;
    private final /* synthetic */ zzki zze;

    zzkk(zzki zzkiVar, Bundle bundle, zzkf zzkfVar, zzkf zzkfVar2, long j2) {
        this.zze = zzkiVar;
        this.zza = bundle;
        this.zzb = zzkfVar;
        this.zzc = zzkfVar2;
        this.zzd = j2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        zzki.c(this.zze, this.zza, this.zzb, this.zzc, this.zzd);
    }
}
