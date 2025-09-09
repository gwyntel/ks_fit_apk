package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzlw implements Runnable {
    private final /* synthetic */ zzmq zza;
    private final /* synthetic */ Runnable zzb;

    zzlw(zzlr zzlrVar, zzmq zzmqVar, Runnable runnable) {
        this.zza = zzmqVar;
        this.zzb = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zza.z();
        this.zza.k(this.zzb);
        this.zza.E();
    }
}
