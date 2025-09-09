package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzkz implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ Bundle zzb;
    private final /* synthetic */ zzkq zzc;

    zzkz(zzkq zzkqVar, zzo zzoVar, Bundle bundle) {
        this.zzc = zzkqVar;
        this.zza = zzoVar;
        this.zzb = bundle;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfh zzfhVar = this.zzc.zzb;
        if (zzfhVar == null) {
            this.zzc.zzj().zzg().zza("Failed to send default event parameters to service");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzfhVar.zza(this.zzb, this.zza);
        } catch (RemoteException e2) {
            this.zzc.zzj().zzg().zza("Failed to send default event parameters to service", e2);
        }
    }
}
