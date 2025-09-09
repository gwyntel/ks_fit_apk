package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;

/* loaded from: classes3.dex */
final class zzlg implements Runnable {
    private final /* synthetic */ boolean zza;
    private final /* synthetic */ zzo zzb;
    private final /* synthetic */ boolean zzc;
    private final /* synthetic */ zzbg zzd;
    private final /* synthetic */ String zze;
    private final /* synthetic */ zzkq zzf;

    zzlg(zzkq zzkqVar, boolean z2, zzo zzoVar, boolean z3, zzbg zzbgVar, String str) {
        this.zzf = zzkqVar;
        this.zza = z2;
        this.zzb = zzoVar;
        this.zzc = z3;
        this.zzd = zzbgVar;
        this.zze = str;
    }

    @Override // java.lang.Runnable
    public final void run() throws Throwable {
        zzfh zzfhVar = this.zzf.zzb;
        if (zzfhVar == null) {
            this.zzf.zzj().zzg().zza("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zza) {
            Preconditions.checkNotNull(this.zzb);
            this.zzf.h(zzfhVar, this.zzc ? null : this.zzd, this.zzb);
        } else {
            try {
                if (TextUtils.isEmpty(this.zze)) {
                    Preconditions.checkNotNull(this.zzb);
                    zzfhVar.zza(this.zzd, this.zzb);
                } else {
                    zzfhVar.zza(this.zzd, this.zze, this.zzf.zzj().zzx());
                }
            } catch (RemoteException e2) {
                this.zzf.zzj().zzg().zza("Failed to send event to the service", e2);
            }
        }
        this.zzf.zzal();
    }
}
