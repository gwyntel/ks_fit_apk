package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzkr implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzkq zzd;

    zzkr(zzkq zzkqVar, AtomicReference atomicReference, zzo zzoVar, boolean z2) {
        this.zzd = zzkqVar;
        this.zza = atomicReference;
        this.zzb = zzoVar;
        this.zzc = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfh zzfhVar;
        synchronized (this.zza) {
            try {
                try {
                    zzfhVar = this.zzd.zzb;
                } catch (RemoteException e2) {
                    this.zzd.zzj().zzg().zza("Failed to get all user properties; remote exception", e2);
                }
                if (zzfhVar == null) {
                    this.zzd.zzj().zzg().zza("Failed to get all user properties; not connected to service");
                    return;
                }
                Preconditions.checkNotNull(this.zzb);
                this.zza.set(zzfhVar.zza(this.zzb, this.zzc));
                this.zzd.zzal();
                this.zza.notify();
            } finally {
                this.zza.notify();
            }
        }
    }
}
