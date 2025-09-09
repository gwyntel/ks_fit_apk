package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzkt implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ boolean zzb;
    private final /* synthetic */ zzmz zzc;
    private final /* synthetic */ zzkq zzd;

    zzkt(zzkq zzkqVar, zzo zzoVar, boolean z2, zzmz zzmzVar) {
        this.zzd = zzkqVar;
        this.zza = zzoVar;
        this.zzb = z2;
        this.zzc = zzmzVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzfh zzfhVar = this.zzd.zzb;
        if (zzfhVar == null) {
            this.zzd.zzj().zzg().zza("Discarding data. Failed to set user property");
            return;
        }
        Preconditions.checkNotNull(this.zza);
        this.zzd.h(zzfhVar, this.zzb ? null : this.zzc, this.zza);
        this.zzd.zzal();
    }
}
