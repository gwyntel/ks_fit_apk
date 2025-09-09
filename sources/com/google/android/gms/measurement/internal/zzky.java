package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzky implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zzb;
    private final /* synthetic */ zzkq zzc;

    zzky(zzkq zzkqVar, zzo zzoVar, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzc = zzkqVar;
        this.zza = zzoVar;
        this.zzb = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            if (!this.zzc.zzk().p().zzh()) {
                this.zzc.zzj().zzv().zza("Analytics storage consent denied; will not get app instance id");
                this.zzc.zzm().k(null);
                this.zzc.zzk().zze.zza(null);
                return;
            }
            zzfh zzfhVar = this.zzc.zzb;
            if (zzfhVar == null) {
                this.zzc.zzj().zzg().zza("Failed to get app instance id");
                return;
            }
            Preconditions.checkNotNull(this.zza);
            String strZzb = zzfhVar.zzb(this.zza);
            if (strZzb != null) {
                this.zzc.zzm().k(strZzb);
                this.zzc.zzk().zze.zza(strZzb);
            }
            this.zzc.zzal();
            this.zzc.zzq().zza(this.zzb, strZzb);
        } catch (RemoteException e2) {
            this.zzc.zzj().zzg().zza("Failed to get app instance id", e2);
        } finally {
            this.zzc.zzq().zza(this.zzb, (String) null);
        }
    }
}
