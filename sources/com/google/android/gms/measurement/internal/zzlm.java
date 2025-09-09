package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzlm implements Runnable {
    private final /* synthetic */ zzfh zza;
    private final /* synthetic */ zzlj zzb;

    zzlm(zzlj zzljVar, zzfh zzfhVar) {
        this.zzb = zzljVar;
        this.zza = zzfhVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        synchronized (this.zzb) {
            try {
                this.zzb.zzb = false;
                if (!this.zzb.f13301a.zzah()) {
                    this.zzb.f13301a.zzj().zzp().zza("Connected to service");
                    this.zzb.f13301a.g(this.zza);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
