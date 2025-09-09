package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzlk implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ String zzb;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ zzo zze;
    private final /* synthetic */ boolean zzf;
    private final /* synthetic */ zzkq zzg;

    zzlk(zzkq zzkqVar, AtomicReference atomicReference, String str, String str2, String str3, zzo zzoVar, boolean z2) {
        this.zzg = zzkqVar;
        this.zza = atomicReference;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = zzoVar;
        this.zzf = z2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzfh zzfhVar;
        synchronized (this.zza) {
            try {
                try {
                    zzfhVar = this.zzg.zzb;
                } catch (RemoteException e2) {
                    this.zzg.zzj().zzg().zza("(legacy) Failed to get user properties; remote exception", zzfs.d(this.zzb), this.zzc, e2);
                    this.zza.set(Collections.emptyList());
                }
                if (zzfhVar == null) {
                    this.zzg.zzj().zzg().zza("(legacy) Failed to get user properties; not connected to service", zzfs.d(this.zzb), this.zzc, this.zzd);
                    this.zza.set(Collections.emptyList());
                    return;
                }
                if (TextUtils.isEmpty(this.zzb)) {
                    Preconditions.checkNotNull(this.zze);
                    this.zza.set(zzfhVar.zza(this.zzc, this.zzd, this.zzf, this.zze));
                } else {
                    this.zza.set(zzfhVar.zza(this.zzb, this.zzc, this.zzd, this.zzf));
                }
                this.zzg.zzal();
                this.zza.notify();
            } finally {
                this.zza.notify();
            }
        }
    }
}
