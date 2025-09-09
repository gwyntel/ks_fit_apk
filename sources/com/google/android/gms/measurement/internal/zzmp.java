package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzmp implements Runnable {
    private final /* synthetic */ zzmx zza;
    private final /* synthetic */ zzmq zzb;

    zzmp(zzmq zzmqVar, zzmx zzmxVar) {
        this.zzb = zzmqVar;
        this.zza = zzmxVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        zzmq.i(this.zzb, this.zza);
        this.zzb.D();
    }
}
