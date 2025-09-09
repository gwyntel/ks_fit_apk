package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzkv implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ zzkq zzc;

    zzkv(zzkq zzkqVar, AtomicReference atomicReference, zzo zzoVar) {
        this.zzc = zzkqVar;
        this.zza = atomicReference;
        this.zzb = zzoVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zza) {
            try {
                try {
                } catch (RemoteException e2) {
                    this.zzc.zzj().zzg().zza("Failed to get app instance id", e2);
                }
                if (!this.zzc.zzk().p().zzh()) {
                    this.zzc.zzj().zzv().zza("Analytics storage consent denied; will not get app instance id");
                    this.zzc.zzm().k(null);
                    this.zzc.zzk().zze.zza(null);
                    this.zza.set(null);
                    return;
                }
                zzfh zzfhVar = this.zzc.zzb;
                if (zzfhVar == null) {
                    this.zzc.zzj().zzg().zza("Failed to get app instance id");
                    return;
                }
                Preconditions.checkNotNull(this.zzb);
                this.zza.set(zzfhVar.zzb(this.zzb));
                String str = (String) this.zza.get();
                if (str != null) {
                    this.zzc.zzm().k(str);
                    this.zzc.zzk().zze.zza(str);
                }
                this.zzc.zzal();
                this.zza.notify();
            } finally {
                this.zza.notify();
            }
        }
    }
}
