package com.google.android.gms.measurement.internal;

/* loaded from: classes3.dex */
final class zzlc extends zzaw {
    private final /* synthetic */ zzkq zza;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzlc(zzkq zzkqVar, zzic zzicVar) {
        super(zzicVar);
        this.zza = zzkqVar;
    }

    @Override // com.google.android.gms.measurement.internal.zzaw
    public final void zzb() {
        this.zza.zzj().zzu().zza("Tasks have been queued for a long time");
    }
}
