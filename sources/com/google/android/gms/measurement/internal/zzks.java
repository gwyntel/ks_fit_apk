package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzks implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzo zzc;
    private final /* synthetic */ boolean zzd;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zze;
    private final /* synthetic */ zzkq zzf;

    zzks(zzkq zzkqVar, String str, String str2, zzo zzoVar, boolean z2, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzf = zzkqVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzoVar;
        this.zzd = z2;
        this.zze = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundle = new Bundle();
        try {
            zzfh zzfhVar = this.zzf.zzb;
            if (zzfhVar == null) {
                this.zzf.zzj().zzg().zza("Failed to get user properties; not connected to service", this.zza, this.zzb);
                return;
            }
            Preconditions.checkNotNull(this.zzc);
            Bundle bundleZza = zzne.zza(zzfhVar.zza(this.zza, this.zzb, this.zzd, this.zzc));
            this.zzf.zzal();
            this.zzf.zzq().zza(this.zze, bundleZza);
        } catch (RemoteException e2) {
            this.zzf.zzj().zzg().zza("Failed to get user properties; remote exception", this.zza, e2);
        } finally {
            this.zzf.zzq().zza(this.zze, bundle);
        }
    }
}
