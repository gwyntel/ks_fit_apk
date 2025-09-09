package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzqs implements zzqp {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.collection.event_safelist", true);
        zzb = zzgtVarZza.zza("measurement.service.store_null_safelist", true);
        zzc = zzgtVarZza.zza("measurement.service.store_safelist", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzqp
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzqp
    public final boolean zzb() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzqp
    public final boolean zzc() {
        return zzc.zza().booleanValue();
    }
}
