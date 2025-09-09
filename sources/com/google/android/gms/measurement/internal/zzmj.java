package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzmj extends zzaw {
    private final /* synthetic */ zzmk zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzmj(zzmk zzmkVar, zzic zzicVar) {
        super(zzicVar);
        this.zza = zzmkVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    public final void zzb() {
        this.zza.zzu();
        this.zza.zzj().zzp().zza("Starting upload from DelayedRunnable");
        this.zza.f13314b.E();
    }
}
