package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzjt implements Runnable {
    private final /* synthetic */ zzay zza;
    private final /* synthetic */ zzin zzb;

    zzjt(zzin zzinVar, zzay zzayVar) {
        this.zzb = zzinVar;
        this.zza = zzayVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        if (this.zzb.zzk().f(this.zza)) {
            this.zzb.zzo().q(false);
        } else {
            this.zzb.zzj().zzn().zza("Lower precedence consent source ignored, proposed source", Integer.valueOf(this.zza.zza()));
        }
    }
}
