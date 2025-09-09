package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzsc implements zzrz {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Double> zzb;
    private static final zzgl<Long> zzc;
    private static final zzgl<Long> zzd;
    private static final zzgl<String> zze;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zza();
        zza = zzgtVarZza.zza("measurement.test.boolean_flag", false);
        zzb = zzgtVarZza.zza("measurement.test.double_flag", -3.0d);
        zzc = zzgtVarZza.zza("measurement.test.int_flag", -2L);
        zzd = zzgtVarZza.zza("measurement.test.long_flag", -1L);
        zze = zzgtVarZza.zza("measurement.test.string_flag", "---");
    }

    @Override // com.google.android.gms.internal.measurement.zzrz
    public final double zza() {
        return zzb.zza().doubleValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzrz
    public final long zzb() {
        return zzc.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzrz
    public final long zzc() {
        return zzd.zza().longValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzrz
    public final String zzd() {
        return zze.zza();
    }

    @Override // com.google.android.gms.internal.measurement.zzrz
    public final boolean zze() {
        return zza.zza().booleanValue();
    }
}
