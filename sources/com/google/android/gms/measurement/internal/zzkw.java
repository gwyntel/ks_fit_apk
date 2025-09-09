package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzkw implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzkq zzb;

    zzkw(zzkq zzkqVar, zzo zzoVar) {
        this.zzb = zzkqVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfh zzfhVar = this.zzb.zzb;
        if (zzfhVar == null) {
            this.zzb.zzj().zzg().zza("Failed to reset data on the service: not connected to service");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzfhVar.zzd(this.zza);
        } catch (RemoteException e2) {
            this.zzb.zzj().zzg().zza("Failed to reset data on the service: remote exception", e2);
        }
        this.zzb.zzal();
    }
}
