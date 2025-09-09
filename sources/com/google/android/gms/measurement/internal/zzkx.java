package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzkx implements Runnable {
    private final /* synthetic */ zzo zza;
    private final /* synthetic */ zzkq zzb;

    zzkx(zzkq zzkqVar, zzo zzoVar) {
        this.zzb = zzkqVar;
        this.zza = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzfh zzfhVar = this.zzb.zzb;
        if (zzfhVar == null) {
            this.zzb.zzj().zzg().zza("Discarding data. Failed to send app launch");
            return;
        }
        try {
            Preconditions.checkNotNull(this.zza);
            zzfhVar.zzc(this.zza);
            this.zzb.zzh().zzac();
            this.zzb.h(zzfhVar, null, this.zza);
            this.zzb.zzal();
        } catch (RemoteException e2) {
            this.zzb.zzj().zzg().zza("Failed to send app launch to the service", e2);
        }
    }
}
