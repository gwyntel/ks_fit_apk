package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzrr implements zzrs {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Long> zzc;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.item_scoped_custom_parameters.client", true);
        zzb = zzgtVarZza.zza("measurement.item_scoped_custom_parameters.service", false);
        zzc = zzgtVarZza.zza("measurement.id.item_scoped_custom_parameters.service", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzrs
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzrs
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzrs
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }
}
