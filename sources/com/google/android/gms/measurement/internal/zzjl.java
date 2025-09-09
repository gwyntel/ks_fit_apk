package com.google.android.gms.measurement.internal;

import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes3.dex */
final class zzjl implements Runnable {
    private final /* synthetic */ AtomicReference zza;
    private final /* synthetic */ String zzb = null;
    private final /* synthetic */ String zzc;
    private final /* synthetic */ String zzd;
    private final /* synthetic */ zzin zze;

    zzjl(zzin zzinVar, AtomicReference atomicReference, String str, String str2, String str3) {
        this.zze = zzinVar;
        this.zza = atomicReference;
        this.zzc = str2;
        this.zzd = str3;
    }

    @Override // java.lang.Runnable
    public final void run() throws IllegalStateException {
        this.zze.f13286a.zzr().n(this.zza, null, this.zzc, this.zzd);
    }
}
