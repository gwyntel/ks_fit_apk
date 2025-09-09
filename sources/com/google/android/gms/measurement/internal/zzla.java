package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* loaded from: classes3.dex */
final class zzla implements Runnable {
    private final /* synthetic */ zzkf zza;
    private final /* synthetic */ zzkq zzb;

    zzla(zzkq zzkqVar, zzkf zzkfVar) {
        this.zzb = zzkqVar;
        this.zza = zzkfVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfh zzfhVar = this.zzb.zzb;
        if (zzfhVar == null) {
            this.zzb.zzj().zzg().zza("Failed to send current screen to service");
            return;
        }
        try {
            zzkf zzkfVar = this.zza;
            if (zzkfVar == null) {
                zzfhVar.zza(0L, (String) null, (String) null, this.zzb.zza().getPackageName());
            } else {
                zzfhVar.zza(zzkfVar.zzc, zzkfVar.zza, zzkfVar.zzb, this.zzb.zza().getPackageName());
            }
            this.zzb.zzal();
        } catch (RemoteException e2) {
            this.zzb.zzj().zzg().zza("Failed to send current screen to the service", e2);
        }
    }
}
