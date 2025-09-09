package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzjo implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ String zzb = null;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ boolean zze;
    private final /* synthetic */ zzin zzf;

    zzjo(zzin zzinVar, AtomicReference atomicReference, String str, String str2, String str3, boolean z2) {
        this.zzf = zzinVar;
        this.zza = atomicReference;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = z2;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zzf.f13286a.zzr().o(this.zza, null, this.zzc, this.zzd, this.zze);
    }
}
