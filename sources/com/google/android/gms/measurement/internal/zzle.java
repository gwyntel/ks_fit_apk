package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzle implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzkq zzb;

    zzle(zzkq zzkqVar, zzo zzoVar) {
        this.zzb = zzkqVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfh zzfhVar = this.zzb.zzb;
        if (zzfhVar == null) {
            this.zzb.zzj().zzg().zza("Failed to send measurementEnabled to service");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzfhVar.zzf(this.zza);
            this.zzb.zzal();
        } catch (RemoteException e2) {
            this.zzb.zzj().zzg().zza("Failed to send measurementEnabled to the service", e2);
        }
    }
}
