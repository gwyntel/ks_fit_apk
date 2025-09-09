package com.google.android.gms.measurement.internal;

import android.os.RemoteException;

/* loaded from: classes3.dex */
final class zzlb implements Runnable {
    private final /* synthetic */ zzbg zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ com.google.android.gms.internal.measurement.zzcv zzc;
    private final /* synthetic */ zzkq zzd;

    zzlb(zzkq zzkqVar, zzbg zzbgVar, String str, com.google.android.gms.internal.measurement.zzcv zzcvVar) {
        this.zzd = zzkqVar;
        this.zza = zzbgVar;
        this.zzb = str;
        this.zzc = zzcvVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            zzfh zzfhVar = this.zzd.zzb;
            if (zzfhVar == null) {
                this.zzd.zzj().zzg().zza("Discarding data. Failed to send event to service to bundle");
                return;
            }
            byte[] bArrZza = zzfhVar.zza(this.zza, this.zzb);
            this.zzd.zzal();
            this.zzd.zzq().zza(this.zzc, bArrZza);
        } catch (RemoteException e2) {
            this.zzd.zzj().zzg().zza("Failed to send event to the service to bundle", e2);
        } finally {
            this.zzd.zzq().zza(this.zzc, (byte[]) null);
        }
    }
}
