package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzlf implements Runnable {
    private final /* synthetic */ boolean zza = true;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzad zzd;
    private final /* synthetic */ zzad zze;
    private final /* synthetic */ zzkq zzf;

    zzlf(zzkq zzkqVar, boolean z2, zzo zzoVar, boolean z3, zzad zzadVar, zzad zzadVar2) {
        this.zzf = zzkqVar;
        this.zzb = zzoVar;
        this.zzc = z3;
        this.zzd = zzadVar;
        this.zze = zzadVar2;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzfh zzfhVar = this.zzf.zzb;
        if (zzfhVar == null) {
            this.zzf.zzj().zzg().zza("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zza) {
            Preconditions.checkNotNull(this.zzb);
            this.zzf.h(zzfhVar, this.zzc ? null : this.zzd, this.zzb);
        } else {
            try {
                if (TextUtils.isEmpty(this.zze.zza)) {
                    Preconditions.checkNotNull(this.zzb);
                    zzfhVar.zza(this.zzd, this.zzb);
                } else {
                    zzfhVar.zza(this.zzd);
                }
            } catch (RemoteException e2) {
                this.zzf.zzj().zzg().zza("Failed to send conditional user property to the service", e2);
            }
        }
        this.zzf.zzal();
    }
}
