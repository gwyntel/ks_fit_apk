package com.google.android.gms.internal.measurement;

/* loaded from: classes3.dex */
public final class zzsi implements zzsf {
    private static final zzgl<Boolean> zza;
    private static final zzgl<Boolean> zzb;
    private static final zzgl<Boolean> zzc;
    private static final zzgl<Boolean> zzd;
    private static final zzgl<Long> zze;

    static {
        zzgt zzgtVarZza = new zzgt(zzgm.zza("com.google.android.gms.measurement")).zzb().zza();
        zza = zzgtVarZza.zza("measurement.rb.attribution.client2", false);
        zzb = zzgtVarZza.zza("measurement.rb.attribution.service", false);
        zzc = zzgtVarZza.zza("measurement.rb.attribution.enable_trigger_redaction", true);
        zzd = zzgtVarZza.zza("measurement.rb.attribution.uuid_generation", true);
        zze = zzgtVarZza.zza("measurement.id.rb.attribution.service", 0L);
    }

    @Override // com.google.android.gms.internal.measurement.zzsf
    public final boolean zza() {
        return true;
    }

    @Override // com.google.android.gms.internal.measurement.zzsf
    public final boolean zzb() {
        return zza.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsf
    public final boolean zzc() {
        return zzb.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsf
    public final boolean zzd() {
        return zzc.zza().booleanValue();
    }

    @Override // com.google.android.gms.internal.measurement.zzsf
    public final boolean zze() {
        return zzd.zza().booleanValue();
    }
}
