package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;

/* loaded from: classes3.dex */
final class zzlh implements Runnable {
    private final /* synthetic */ String zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ zzo zzc;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zzd;
    private final /* synthetic */ zzkq zze;

    zzlh(zzkq zzkqVar, String str, String str2, zzo zzoVar, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zze = zzkqVar;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzoVar;
        this.zzd = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList<Bundle> arrayList = new ArrayList<>();
        try {
            zzfh zzfhVar = this.zze.zzb;
            if (zzfhVar == null) {
                this.zze.zzj().zzg().zza("Failed to get conditional properties; not connected to service", this.zza, this.zzb);
                return;
            }
            Preconditions.checkNotNull(this.zzc);
            ArrayList<Bundle> arrayListZzb = zzne.zzb(zzfhVar.zza(this.zza, this.zzb, this.zzc));
            this.zze.zzal();
            this.zze.zzq().zza(this.zzd, arrayListZzb);
        } catch (RemoteException e2) {
            this.zze.zzj().zzg().zza("Failed to get conditional properties; remote exception", this.zza, this.zzb, e2);
        } finally {
            this.zze.zzq().zza(this.zzd, arrayList);
        }
    }
}
